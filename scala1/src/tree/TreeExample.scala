package tree

object TreeExample {
	type Environment = String => Int
	def eval(t: Tree, env: Environment):Int = t match {
	  case Sum(l, r) => eval(l, env) + eval(r, env)
	  case Var(n) => env(n)
	  case Const(v) => v
	}
	
	def derive(t: Tree, v:String) : Tree = t match {
	  case Sum(l, r) => Sum(derive(l, v), derive(r, v))
	  case Var(n) if(v==n) => Const(1)
	  case _ => Const(0)
	}
	def main(args: Array[String]) {
    }
}