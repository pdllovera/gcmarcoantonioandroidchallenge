package garcia.marco.androidchallenge.domain.exceptions

class DataException(val description : String, override val message: String? = null) : Exception() {
}