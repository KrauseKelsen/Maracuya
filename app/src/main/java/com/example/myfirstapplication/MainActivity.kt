package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.lookandfeel.BiometricsDisclaimerScreen
import com.example.myfirstapplication.lookandfeel.LoginMainScreen
import com.example.myfirstapplication.lookandfeel.LoginScreen
import com.example.myfirstapplication.lookandfeel.OneTimeDisclamerScreen
import com.example.myfirstapplication.lookandfeel.VerifyCodeScreen
import cruxui.android.maracuya.compositions.LocalFontFamily
import cruxui.android.maracuya.compositions.LocalLibraryIcons
import cruxui.android.maracuya.theme.MyLibraryTheme
import cruxui.android.maracuya.tokens.base.ColorToken
import cruxui.android.maracuya.tokens.base.TypographyToken
import cruxui.android.maracuya.ui.components.utils.IconPosition
import cruxui.android.maracuya.ui.components.buttons.button.ButtonMrcy
import cruxui.android.maracuya.ui.components.buttons.button.ButtonTokensResolver
import cruxui.android.maracuya.ui.components.buttons.chipchoice.carousel.ChipChoiceItem
import cruxui.android.maracuya.ui.components.dropdowns.DropDownContent
import cruxui.android.maracuya.ui.components.dropdowns.DropDownItem
import cruxui.android.maracuya.ui.components.dropdowns.DropDownMrcy
import cruxui.android.maracuya.ui.components.dropdowns.DropDownSelectionMode
import cruxui.android.maracuya.ui.components.dropdowns.DropDownVariant
import cruxui.android.maracuya.ui.components.inputs.basic.InputFieldBasicMrcy
import cruxui.android.maracuya.ui.components.inputs.basic.InputFieldBasicType
import cruxui.android.maracuya.ui.components.inputs.config.InputFieldBasicTokenGroup
import cruxui.android.maracuya.ui.components.inputs.config.InputFieldBasicTokensResolver
import cruxui.android.maracuya.ui.components.labels.LabelMrcy
import cruxui.android.maracuya.ui.components.textfields.TextFieldMrcy
import cruxui.android.maracuya.ui.components.textfields.TextFieldVariant
import cruxui.android.maracuya.ui.components.texts.LegalText
import cruxui.android.maracuya.ui.components.texts.LegalTextTokens
import cruxui.android.maracuya.ui.previews.core.Mode
import cruxui.android.maracuya.ui.previews.core.PreviewWrapper


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                // Scaffold usa el background del tema DS
                // No esta implementado para poder ver las letras
//                Scaffold(
//                    modifier = Modifier.fillMaxSize(),
//                ) { innerPadding ->
//                    MyLibraryTheme(
//                        theme = LibraryThemes.Maracuya,
//                        style = LibraryThemeStyles.AUTO,
//                        useMaterial = false
//                    ) {
//                        DemoScreen(modifier = Modifier.padding(innerPadding))
//                    }
//

            //LoginMainScreen()

            //LoginScreen()
            //VerifyCodeScreen()
            //BiometricsDisclaimerScreen()
            //OneTimeDisclamerScreen()

//            var selectedIds by remember { mutableStateOf(setOf("national")) }
//
//            val content = DropDownContent.Simple(
//                items = listOf(
//                    DropDownItem(id = "national", label = "Nacional"),
//                    DropDownItem(id = "passport", label = "Pasaporte"),
//                    DropDownItem(id = "foreign", label = "Cédula extranjera"),
//                ),
//            )
//
//            MyLibraryTheme {
//                 DropDownMrcy(
//                     selectedIds = selectedIds,
//                     onSelectionChange = { selectedIds = it },
//                     label = "Tipo de identificación",
//                     placeholder = "Selecciona una opción",
//                     content = content,
//                     dropDownVariant = DropDownVariant.SIMPLE,
//                     bottomText = "Selecciona un tipo de documento",
//                 )
//             }

            LoginMainScreen()
        }
    }
}




private val baseItems = listOf(
    ChipChoiceItem(id = "1", label = "Item 1"),
    ChipChoiceItem(id = "2", label = "Item 2"),
    ChipChoiceItem(id = "3", label = "Item 3"),
)

private val overflowItems = (1..12).map {
    ChipChoiceItem(
        id = it.toString(),
        label = "Item $it"
    )
}

@Composable
fun DemoScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),

        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabelMrcy(text = "Ingrese con su usuario de siempre de Davibank o Davivienda.")

            var value by remember { mutableStateOf("") }
            TextFieldMrcy(
                value = value,
                onValueChange = { value = it },
                label = "Usuario",
                placeholder = "Daviuser1234",
                textFieldVariant = TextFieldVariant.FACE_ID,
            )

            //USER_WITH_FACE_ID no se muestra el user
            //se debe borrar el clear porque esta implicito en clear

            InputFieldBasicMrcy(
                value = value,
                placeholder = "Daviuser1234",
                onValueChange = { value = it },
                inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                        group = InputFieldBasicTokenGroup.LEADING_KEY_TRAILING_VISIBILITY
                ),
            )

            InputFieldBasicMrcy(
                value = value,
                placeholder = "Daviuser1234",
                onValueChange = { value = it },
                enableImplicitTrailingClear = true,
                inputFieldBasicTokens = InputFieldBasicTokensResolver.resolve(
                    group = InputFieldBasicTokenGroup.BASIC
                ),
                hasError = true
            )

//            ChipChoiceBaseMrcy(
//                text = "Item",
//                onClick = {
//                    // acción del chip
//                },
//                enabled = false
//            )

            Spacer(modifier = Modifier.height(12.dp))

//            Host_UseMaterialMode()

            //Spacer(modifier = Modifier.height(12.dp))

            //Host_CustomButtonTokensMode()

            //Spacer(modifier = Modifier.height(12.dp))

            //Host_UseLibrarySemanticsMode()

            //Spacer(modifier = Modifier.height(12.dp))

            //Host_UseTypographyModes()

            //Host_LegalText()

            //ExampleScreen()

//            Spacer(modifier = Modifier.height(12.dp))
//
//
//            var value by remember { mutableStateOf("") }
//
//            TextFieldMrcy(
//                value = value,
//                onValueChange = { value = it },
//                label = "Label",
//                placeholder = "Placeholder",
//                hasError = true
//            )
//
//            TextFieldMrcy(
//                value = value,
//                onValueChange = { value = it },
//                label = "Label",
//                placeholder = "Placeholder",
//                hasError = false,
//                bottomText = "Bottom text"
//            )
        }
    }
}


@Composable
private fun ExampleScreen() {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🟢 Estado normal (texto)
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Nombre completo",
            onValueChange = { name = it },
            inputType = InputFieldBasicType.TEXT
        )

        // 🔢 Solo números (teclado numérico)
        InputFieldBasicMrcy(
            value = age,
            placeholder = "Edad",
            onValueChange = { age = it },
            inputType = InputFieldBasicType.NUMBER
        )

        // 🔴 Estado de error (el input NO sabe por qué)
        InputFieldBasicMrcy(
            value = name,
            placeholder = "Nombre (error)",
            onValueChange = { name = it },
            hasError = true
        )

        // 🚫 Deshabilitado
        InputFieldBasicMrcy(
            value = "No editable",
            placeholder = "Disabled",
            onValueChange = {},
            enabled = false
        )

        // 👁 Read only
        InputFieldBasicMrcy(
            value = "Solo lectura",
            placeholder = "Read only",
            onValueChange = {},
            readOnly = true
        )
    }
}


/*
@Preview(showBackground = true)
@Composable
fun DemoScreenPreview() {
    DemoScreen()
}
*/

/**
 * Boton Maracuya: Semantica corporativa con puente a MD3
 * Boton Compose nativo: Semantica corporativa con puente a MD3
 **/
@Composable
fun Host_UseMaterialMode() {

    ButtonMrcy(
        text = "Maracuya puro",
        onClick = {},
    )


        // material del host

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Material Design 3 AppHost")
        }

//    // material de maracuya con puente semantico
//    MyLibraryTheme(
//        theme = LibraryThemes.Maracuya,
//        style = LibraryThemeStyles.AUTO,
//        useMaterial = true
//    ) {
//        Button(
//            onClick = {},
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.secondary
//            )
//        ) {
//            Text("MD3 de Maracuya")
//        }
//    }

}




/**
 * Ejemplo: Tipografía en distintos modos
 **/

/**
 * Textos nativos: Semantica corporativa sin MD3
 **/

@Composable
fun Host_UseTypographyModes() {

//    MyLibraryTheme(
//        theme = LibraryThemes.Maracuya,
//        style = LibraryThemeStyles.AUTO
//    ) {
//
//        Column(
//            modifier = Modifier.fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            Text(
//                text = "Tipografía corporativa (SemanticTypography.body1)",
//                style = SemanticTypography.body1
//            )
//
//            Text(
//                text = "Tipografía modificada con weight = Bold",
//                style = SemanticTypography.body1.with(weight = FontWeightTokens.Bold)
//            )
//
//            Text(
//                text = "Tipografía personalizada (Custom FontFamily)",
//                style = SemanticTypography.body1.copy(
//                    fontFamily = FontFamily.Serif
//                )
//            )
//
//            Text(
//                text = "Tipografía desde MaterialTheme (host theme)",
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//    }
}


@Composable
fun Host_LegalText() {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LegalText("Términos y condiciones. Texto de ejemplo.")
            LegalText("Términos Davivienda en negrita", textTokens = LegalTextTokens.fromMaterial())
            LegalText("Términos Davivienda Tokens de Maracuya", textTokens = LegalTextTokens.fromCorporate())
        }
}