package com.example.pmanager.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var fontSize by remember { mutableStateOf(16.sp) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Setting",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Font size",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "A",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Slider(
                            value = fontSize.value,
                            onValueChange = { fontSize = it.sp },
                            valueRange = 12f..24f,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Text(
                            text = "A",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = "Display - ${fontSize.value.toInt()}sp",
                        fontSize = fontSize,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            PlaceholderSettingItem(
                icon = Icons.Outlined.Palette,
                title = "Color",
                description = "Lorem ipsum dolor sit amet"
            )

            PlaceholderSettingItem(
                icon = Icons.Outlined.Nightlight,
                title = "Dark mode",
                description = "Consectetur adipiscing elit"
            )

            PlaceholderSettingItem(
                icon = Icons.Outlined.Translate,
                title = "Language",
                description = "Sed do eiusmod tempor incididunt"
            )

            PlaceholderSettingItem(
                icon = Icons.Outlined.Cloud,
                title = "Cloud",
                description = "Ut labore et dolore magna aliqua"
            )
        }
    }
}

@Composable
private fun PlaceholderSettingItem(
    icon: ImageVector,
    title: String,
    description: String
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { /* It's nothing here……for now*/ },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}
