package com.delecrode.devhub.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.delecrode.devhub.R
import com.delecrode.devhub.ui.theme.DevHubTheme
import com.delecrode.devhub.ui.theme.PrimaryBlue

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Email",
    isError: Boolean = false,
    errorMessage: String = "",
    imeAction: ImeAction = ImeAction.Next
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(8.dp)
                ),
            placeholder = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = PrimaryBlue
                )
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview() {
    DevHubTheme() {
        EmailTextField(
            value = "user@example.com",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Senha",
    isError: Boolean = false,
    errorMessage: String = "",
    imeAction: ImeAction = ImeAction.Done,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (() -> Unit)? = null
) {
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(isPasswordVisible) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp)),
            placeholder = { Text(label) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = PrimaryBlue
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (onVisibilityChange != null) {
                        onVisibilityChange()
                    } else {
                        passwordVisible = !passwordVisible
                    }
                }) {
                    Icon(
                        painter = if (onVisibilityChange != null) {
                            if (isPasswordVisible) painterResource(R.drawable.ic_visibility_off_24) else painterResource(
                                R.drawable.ic_visibility_on_24
                            )
                        } else {
                            if (passwordVisible) painterResource(R.drawable.ic_visibility_off_24) else painterResource(
                                R.drawable.ic_visibility_on_24
                            )
                        },
                        contentDescription = if (onVisibilityChange != null) {
                            if (isPasswordVisible) "Hide password" else "Show password"
                        } else {
                            if (passwordVisible) "Hide password" else "Show password"
                        },
                        tint = PrimaryBlue
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            visualTransformation = if (onVisibilityChange != null) {
                if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    DevHubTheme() {
        PasswordTextField(
            value = "password123",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun GenericOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    leadingIcon: ImageVector? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(label) },
            leadingIcon = {
                leadingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = PrimaryBlue
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(8.dp)
        )

        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GenericOutlinedTextFieldPreview() {
    DevHubTheme() {
        GenericOutlinedTextField(
            value = "password123",
            onValueChange = {},
            label = "Usuario"
        )
    }
}


