package com.example.ecomm.core.components

import android.R.attr.singleLine
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.ecomm.core.theme.AppColors

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable ((isVisible: Boolean, toggle: () -> Unit) -> Unit)? = null,
    obscureText: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    onDone: ((String) -> Unit)? = null,
) {
    var showPassword by remember { mutableStateOf(false) }
    val  isDark = isSystemInDarkTheme()
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = hintText?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon?.let { icon ->
            { icon(showPassword) { showPassword = !showPassword } }
        },
        visualTransformation = if (obscureText && !showPassword)  PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone?.invoke(value) },
            onNext = { onDone?.invoke(value) },
            onSearch = { onDone?.invoke(value) },
            onSend = { onDone?.invoke(value) }
        ),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.W500
        ),
        isError = isError,
        supportingText = errorMessage?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColors.primary,
            unfocusedBorderColor =  if(isDark) Color.Transparent else AppColors.border,
            cursorColor = AppColors.primary,
            errorBorderColor = AppColors.danger,
            errorTextColor = AppColors.danger,
            unfocusedPlaceholderColor = AppColors.iconMuted,
            unfocusedLeadingIconColor = AppColors.iconMuted,
            focusedLeadingIconColor = AppColors.iconMuted,
            focusedPlaceholderColor = AppColors.iconMuted,
            errorPlaceholderColor = AppColors.iconMuted,
            errorLeadingIconColor = AppColors.iconMuted,
            unfocusedContainerColor =  if(isDark) AppColors.darkInputFill else AppColors.inputBackground
        )
    )
}