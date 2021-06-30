# graphql-task


This app is based on MVVM architecture. It is a single activity app with two fragments. One representing posts list and one for displaying post detail.

I applied separation of concern by creating layers of architecture by creating data, remote and ui packages.

I used following Android Libraries:

  * GraphQl Apollo - To generate Java and Kotlin models from GraphQL queries
  * Hilt - For Dependency Injection
  * Coroutines - To Manage async calls in reactive way
  * Data Binding - Declaratively bind observable data to UI elements.
  * Android KTX - Write more concise, idiomatic Kotlin code.
  * AppCompat - Degrade gracefully on older versions of Android
  * Lifecycles - Create a UI that automatically responds to lifecycle events.
  * LiveData - Build data objects that notify views when the underlying database changes.
  * Navigation - Handle everything needed for in-app navigation.
  * ViewModel - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.

 ## Test
 
 I wrote Unit Tests for Repository and ViewModels.
