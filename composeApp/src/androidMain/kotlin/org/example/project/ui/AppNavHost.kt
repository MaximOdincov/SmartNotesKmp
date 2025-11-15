import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.ui.NoteViewModel
import androidx.compose.runtime.collectAsState
import org.example.project.ui.NoteEditScreen
import org.example.project.ui.ScreenState

@Composable
fun AppNavHost(vm: NoteViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            NotesListScreen(
                vm = vm,
                onOpenNote = { note ->
                    navController.navigate("edit/${note.id}")
                }
            )
        }

        composable("edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val note = (vm.state.collectAsState().value as? ScreenState.Content)
                ?.notes
                ?.find { it.id == id }

            NoteEditScreen(
                note = note,
                vm = vm,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
