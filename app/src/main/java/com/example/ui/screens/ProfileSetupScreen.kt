package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.DeepTealContainer
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCoral
import com.example.ui.theme.WarmCoralContainer
import com.example.viewmodel.MaitryViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen(
    viewModel: MaitryViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(viewModel.inputName.value) }
    var city by remember { mutableStateOf(viewModel.inputCity.value) }
    var gender by remember { mutableStateOf(viewModel.inputGender.value) }
    var bio by remember { mutableStateOf(viewModel.inputBio.value) }
    var intent by remember { mutableStateOf(viewModel.inputIntent.value) }
    var consentChecked by remember { mutableStateOf(true) }

    val age by viewModel.inputAge.collectAsState()
    val selectedInterests by viewModel.selectedInterests.collectAsState()
    val selectedLanguages by viewModel.selectedLanguages.collectAsState()

    val cityOptions = listOf("Mumbai", "Bengaluru", "Delhi NCR", "Pune", "Hyderabad", "Kolkata", "Chennai", "Ahmedabad", "Jaipur", "Chandigarh")
    val genderOptions = listOf("Woman", "Man", "Non-binary", "Prefer not to say")
    val intentOptions = listOf(
        "Friendship first",
        "Friendship leading to relationship",
        "Serious relationship",
        "Friendship / Second Innings"
    )
    val availableInterests = listOf(
        "Chai Conversations", "Filter Coffee", "Books & Poetry", "Indie Music",
        "Weekend Treks", "Hindustani Classical", "World Cinema", "Heritage Walks",
        "Board Games", "Cooking & Food Walks", "Yoga & Wellness", "Tech & Startups"
    )
    val availableLanguages = listOf(
        "English", "Hindi", "Marathi", "Bengali", "Tamil", "Telugu", "Kannada", "Malayalam", "Gujarati", "Punjabi"
    )

    var cityExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CreamBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = DeepTeal)
                }
                Text(
                    text = "Create Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepTeal
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Basic Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it; viewModel.inputName.value = it },
                        label = { Text("Display Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = SurfaceVariantWarm,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.CenterStart,
                                modifier = Modifier.padding(horizontal = 14.dp)
                            ) {
                                Text(
                                    text = "Age: $age yrs",
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimaryDark,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = cityExpanded,
                            onExpandedChange = { cityExpanded = !cityExpanded },
                            modifier = Modifier.weight(1.4f)
                        ) {
                            OutlinedTextField(
                                value = city,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("City") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cityExpanded) },
                                modifier = Modifier.menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = cityExpanded,
                                onDismissRequest = { cityExpanded = false }
                            ) {
                                cityOptions.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt) },
                                        onClick = {
                                            city = opt
                                            viewModel.inputCity.value = opt
                                            cityExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Gender",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimaryDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        genderOptions.forEach { g ->
                            FilterChip(
                                selected = gender == g,
                                onClick = { gender = g; viewModel.inputGender.value = g },
                                label = { Text(g, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DeepTealContainer,
                                    selectedLabelColor = DeepTeal
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "What are you looking for on Maitry?",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        intentOptions.forEach { opt ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (intent == opt) WarmCoralContainer else SurfaceVariantWarm,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        intent = opt
                                        viewModel.inputIntent.value = opt
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (intent == opt) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = WarmCoral, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(
                                        text = opt,
                                        fontSize = 13.sp,
                                        fontWeight = if (intent == opt) FontWeight.Bold else FontWeight.Normal,
                                        color = if (intent == opt) Color(0xFF6B1D0E) else TextPrimaryDark
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it; viewModel.inputBio.value = it },
                        label = { Text("About Me (Bio)") },
                        placeholder = { Text("Share what you love, your favorite conversations, and what friendship means to you...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 4
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CreamSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .border(1.dp, Color(0xFFE2E9EA), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Interests & Passions",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepTeal
                    )
                    Text(
                        text = "Pick at least 3 to help match conversations",
                        fontSize = 11.sp,
                        color = TextSecondaryMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        availableInterests.forEach { interest ->
                            val isSelected = selectedInterests.contains(interest)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    val current = viewModel.selectedInterests.value.toMutableSet()
                                    if (isSelected) current.remove(interest) else current.add(interest)
                                    viewModel.selectedInterests.value = current
                                },
                                label = { Text(interest, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DeepTealContainer,
                                    selectedLabelColor = DeepTeal
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Languages You Speak",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepTeal
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        availableLanguages.forEach { lang ->
                            val isSelected = selectedLanguages.contains(lang)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    val current = viewModel.selectedLanguages.value.toMutableSet()
                                    if (isSelected) current.remove(lang) else current.add(lang)
                                    viewModel.selectedLanguages.value = current
                                },
                                label = { Text(lang, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WarmCoralContainer,
                                    selectedLabelColor = Color(0xFF6B1D0E)
                                )
                            )
                        }
                    }
                }
            }
        }

        // DPDP Consent Checkbox
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = consentChecked,
                    onCheckedChange = { consentChecked = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "I explicitly consent to the collection and processing of my profile details solely for matchmaking purposes on Maitry under the Digital Personal Data Protection (DPDP) Act 2023. I can export or delete my data anytime.",
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = TextSecondaryMuted
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (consentChecked) {
                        viewModel.saveProfile()
                    }
                },
                enabled = consentChecked && name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 500.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DeepTeal)
            ) {
                Text("Continue to Verification", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
