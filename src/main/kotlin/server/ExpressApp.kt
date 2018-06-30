package server

external class ExpressApp {
    fun get(route: String, handle: (req: dynamic, res: Response) -> Unit)
    fun post(route: String, handle: (req: dynamic, res: Response) -> Unit)
    fun put(route: String, handle: (req: dynamic, res: Response) -> Unit)

    fun listen(i: Int, function: () -> Unit)
}

external interface Response {
    fun send(data: Any?)
    fun status(code: Int)
}