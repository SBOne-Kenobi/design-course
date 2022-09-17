package processors

import SessionContext

abstract class UserProcessor(val context: SessionContext) {

    abstract fun process()

}