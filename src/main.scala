object Main extends App {
  val username = args.headOption.getOrElse("anonymous")
  val svg = identicon.svg(username)
  System.err.println(s"Identicon for user '$username'")
  println(svg)
}
