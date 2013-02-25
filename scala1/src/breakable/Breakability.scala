package breakable

import scala.util.control.Breaks.{ break, breakable }

/**
 * もともとScalaのbreakableというやつを使ってみる例だったけれど、
 * whileを再帰で置き換えてみるときに無名関数の再帰をしたいがために
 * Yコンビネータなるものを試してみた記録。
 *
 */
object Breakability {

  /**
   * breakable というやつを使ってみる例
   */
  def useBreak() {
    var n = 0
    breakable {
      while (true) {
        println(n)
        n += 1
        if (n >= 5) break
      }
    }
  }

  /**
   * Y combinator (http://yuroyoro.hatenablog.com/entry/20100204/1265285318)
   *
   * 実装動機は次の通り：
   *
   * 「再帰を含む無名関数を定義して使いたい（ただし定義の中では一時的に名前が要る）」
   * 2013-02-25: 再帰の最適化がどうなるのかよくわかってない。
   *
   *
   * 関数渡しは関数が実際に適用されるまで評価されないのは当たり前なんだけど混乱するので注意。
   * 色々と頭が固いので、まずはScalaの定義をHaskell風に換えておいて考えてみたい。
   *
   * y::((a->b)->a->b)->a->b
   * y f x = f (\z -> (y f z)) x
   *
   * 之に対して例えば以下を適用するわけだ。
   *
   * result = y inner (0,"z")
   *          where inner _ (5,s) = s
   *                inner g (n,s) = g (n+1, (show n)++s)
   * {-
   *   result
   *   → y inner (0,"z")
   *   → inner (\z->(y inner z)) (0,"z")
   *   → (\z->(y inner z)) $ (0+1, (show 0)++"z")
   *   → y inner (1, "0z")
   *   → ...
   *   → y inner (5, "43210z")
   *   → inner (\z->(y inner z)) (5, "43210z")
   *   → "43210z"
   * -}
   *
   * yに渡すfは「計算をするし、関数gを貰ってそいつにこの例なら次のタプルを渡して再帰を下らせる」。
   * yはfと初期値xを貰ってfにxと自作のgを渡す。このgは次のタプルを貰ってy自身を再帰呼び出す。
   *
   * 再帰できるのはわかったけど、ラムダ計算との同値性がわからない。
   * yの定義にyが登場しているのがなんとなく反則っぽいのかな。というのも
   * Wikipediaに載ってるハスケル・カリーの定義
   * Y = λf . (λg . f (g g)) (λg . f (g g))
   *
   * ではイコールの右辺にYが出てこない。ラムダ計算では再帰的定義しちゃいけないらしいので。
   * 
   * ちなみにWikipediaではf,xを使っていたけど、私の認知の歪みがあるためf,gのほうがわかりやすいから書き換えた。
   * 右辺の文字は全てλにくっついている文字なので、fもgも関数をあらわす。
   * 
   * いっぽう先に見てきた例では引数に関数以外のもの（初期値、(0,"z")みたいなやつ)をもらってしまっている。
   * これは現実にはともかく理想的にはいらないはず。ということでλ計算の定義どおりにHaskellを書いてみよう。
   * Y = \f -> (\g -> f (g g)) (\g -> f (g g))
   * このままじゃ動かないかもしれないけど、これはやりたいことは
   * http://d.hatena.ne.jp/kazu-yamamoto/20100519/1274240859
   * にかかれてるSLLというのの定義
   * s x y z = x z (y z)
   * l x y = x (y (unsafeCoerce y))
   * y = s l l
   * を展開すれば
   * y = s l l
   *   --> \z -> ((\x,y->x (y y)) z) ((\x,y->x (y y)) z)
   *   --> \z -> (\y->z (y y)) (\y->z (y y))
   * ということで、確かにこのSLLというのがラムダ計算と同じなのはわかった。
   * 
   * @param f
   * @param x
   * @return
   */
  def y[A, B](f: ((A => B), A) => B, x: A): B =
    f((z: A) => y(f, z), x)

  def dontuseBreak() {
    y((g: Int => Int, n: Int) =>
      n match {
        case 5 => n
        case _ => println(n); g(n + 1)
      }, 0)
  }

  /**
   * よくわからないので分解
   */
  def dontuseBreakExplained() {
    def inner(g: Int => Int, n: Int) = {
      n match {
        case 5 =>
          println("end"); n
        case _ => println(n); g(n + 1)
      }
    }
    y(inner, 0)
    // -->  [1] inner((z:A)=>y(inner,z), 0)
    // -->  [2] g(0+1) //innerを展開
    // -->  [3] println(1); y(inner, 1)
    // -->  ...
    // -->  [4] y(inner, 5)
    // -->  [5] println("end"); 5
  }

  def main(args: Array[String]) {
    useBreak()
    println("---")
    dontuseBreak()
    println("---")
    dontuseBreakExplained()
  }
}