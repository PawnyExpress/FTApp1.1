# Database Implementation Branch
 - This branch is here to combine David's UI with my Database code.


## Things to keep in mind for future UI screens
 - Passing context from the MainActivity has already been handled. Any new screens created that want to access the database 
	HAVE to have the databaseHandler variable passed through from IncExpNavGraph.kt
	
	 - When calling on a new screen it must pass in the variable as shown         
		`composable(route = IncomeEntryDestination.route) {
            IncomeEntryScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}, true , databaseHandler)
        }`
		
	Notice the line `onNavigateUp = { navController.navigateUp()}, true , databaseHandler)`
	where databaseHandler is sent through to the IncomeEntryScreen.kt file.
		
	This is only possible due to adding the variable into the constructor for IncomeEntryScreen as shown below
	
	`fun IncomeEntryScreen(
		navigateBack: () -> Unit,
		onNavigateUp: () -> Unit,
		canNavigateBack: Boolean = true,
		databaseHandler : DatabaseHandler,
		viewModel: IncomeExpenseEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
	)`
		
	You must also add `viewModel.sendContext(databaseHandler)` to the any screen file requiring access to the db.
	
This is likely confusing as it was to figure out and this was written at 1:30am, hopefully I recall as well as I want to for tomorrow.