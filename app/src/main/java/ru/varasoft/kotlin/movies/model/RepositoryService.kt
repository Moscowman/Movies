package ru.varasoft.kotlin.movies.model

import android.app.IntentService
import android.content.Intent
import android.content.Context

// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_LOAD_MOVIES = "ru.varasoft.kotlin.movies.model.action.load_movies"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "ru.varasoft.kotlin.movies.model.extra.PARAM1"
private const val EXTRA_PARAM2 = "ru.varasoft.kotlin.movies.model.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class RepositoryService : IntentService("RepositoryService") {

    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_LOAD_MOVIES -> {
                val param1 = intent.getStringExtra(EXTRA_PARAM1)
                val param2 = intent.getStringExtra(EXTRA_PARAM2)
                handleActionLoadMovies(param1, param2)
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionLoadMovies(param1: String, param2: String) {
        TODO("Handle action Foo")
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionLoadMovies(context: Context, param1: String, param2: String) {
            val intent = Intent(context, RepositoryService::class.java).apply {
                action = ACTION_LOAD_MOVIES
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }
    }
}