package by.footballcompetition.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

sealed class Resource<out T> {
    fun <R> mapResource(transform: (T) -> (R)): Resource<R> =
          when (this) {
              is LoadingResource -> LoadingResource
              is ErrorResource -> ErrorResource(error)
              is ContentResource -> ContentResource(transform(content))
          }
}

object LoadingResource : Resource<Nothing>()
data class ContentResource<out T>(val content: T) : Resource<T>()
data class ErrorResource<out T>(val error: Throwable?) : Resource<T>()

fun <X, Y> LiveData<Resource<X>>.mapResource(transform: (X) -> Y): LiveData<Resource<Y>> =
      Transformations.map(this) {
          it?.mapResource(transform)
      }

fun Resource<List<Any>>?.hasContent() = (this as? ContentResource<List<Any>>)?.content?.isNotEmpty() ?: true