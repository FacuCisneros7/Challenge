package org.example.challenge.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.challenge.domain.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit,
    onUserClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var isEditingReview by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.match == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = uiState.errorMessage ?: "Partido no encontrado",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBackClick) { Text("Volver") }
                }
            }
        } else {
            val match = uiState.match!!

            // Format date as dd/MM/yy
            val formattedDate = try {
                val localDateTime = match.date.toLocalDateTime(TimeZone.currentSystemDefault())
                val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
                val month = localDateTime.monthNumber.toString().padStart(2, '0')
                val year = (localDateTime.year % 100).toString().padStart(2, '0')
                "$day/$month/$year"
            } catch (e: Exception) {
                ""
            }

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = match.competition.ifBlank { "Detalle de Partido" },
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Volver"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { viewModel.toggleFavorite() }) {
                                Icon(
                                    imageVector = if (uiState.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = if (uiState.isFavorite) "Quitar de guardados" else "Guardar partido",
                                    tint = if (uiState.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Match Hero Header (Two Badges Layout with Centered Balanced Proportions & Date)
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(270.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF1E293B),
                                            Color(0xFF0F172A),
                                            Color(0xFF020617)
                                        )
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Badges Area with equal proportions (weight 1f each)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        if (match.homeTeamUrl.isNotBlank()) {
                                            AsyncImage(
                                                model = match.homeTeamUrl,
                                                contentDescription = match.homeTeam,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier.size(72.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = "VS",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        if (match.awayTeamUrl.isNotBlank()) {
                                            AsyncImage(
                                                model = match.awayTeamUrl,
                                                contentDescription = match.awayTeam,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier.size(72.dp)
                                            )
                                        }
                                    }
                                }

                                // Bottom Portion: Team Names & Score perfectly centered and balanced
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier.weight(1f),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Text(
                                                text = match.homeTeam,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                maxLines = 1,
                                                textAlign = TextAlign.End
                                            )
                                        }

                                        Text(
                                            text = if (match.formattedScore.isNotEmpty()) match.formattedScore else "vs",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary, // Green color
                                            modifier = Modifier.padding(horizontal = 12.dp),
                                            textAlign = TextAlign.Center
                                        )

                                        Box(
                                            modifier = Modifier.weight(1f),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Text(
                                                text = match.awayTeam,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                maxLines = 1,
                                                textAlign = TextAlign.Start
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (match.stadium.isNotBlank()) {
                                            Text(
                                                text = "${match.stadium}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        if (match.stadium.isNotBlank() && formattedDate.isNotBlank()) {
                                            Text(
                                                text = "  •  ",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        if (formattedDate.isNotBlank()) {
                                            Text(
                                                text = "$formattedDate",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Average Rating Summary Bar
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Puntuación Promedio",
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        if (uiState.reviews.isNotEmpty()) {
                                            Text(
                                                text = "⭐ ${kotlin.math.round(uiState.averageRating * 10) / 10.0}",
                                                style = MaterialTheme.typography.headlineSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                        Text(
                                            text = "${uiState.reviews.size} reseñas",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Button(
                                    onClick = { viewModel.toggleFavorite() },
                                    colors = if (uiState.isFavorite) {
                                        ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    } else {
                                        ButtonDefaults.buttonColors()
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (uiState.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(if (uiState.isFavorite) "Guardado" else "Guardar")
                                }
                            }
                        }
                    }

                    // User Review Form or Existing Review
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            Text(
                                text = "Tu Reseña",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            if (uiState.errorMessage != null) {
                                Text(
                                    text = uiState.errorMessage!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            if (uiState.userReview != null && !isEditingReview) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "⭐ ${uiState.userReview!!.rating}/5",
                                                style = MaterialTheme.typography.titleLarge,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Row {
                                                TextButton(onClick = { isEditingReview = true }) {
                                                    Text("Editar")
                                                }
                                                TextButton(
                                                    onClick = { viewModel.deleteReview(uiState.userReview!!.id) }
                                                ) {
                                                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                                }
                                            }
                                        }
                                        if (uiState.userReview!!.text.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = uiState.userReview!!.text,
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    }
                                }
                            } else {
                                ReviewForm(
                                    initialRating = uiState.userReview?.rating ?: 5,
                                    initialText = uiState.userReview?.text ?: "",
                                    isSubmitting = uiState.isSubmitting,
                                    onSave = { rating, text ->
                                        viewModel.saveReview(rating, text)
                                        isEditingReview = false
                                    },
                                    onCancel = if (isEditingReview) { { isEditingReview = false } } else null
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Community Reviews Header
                    item {
                        Text(
                            text = "Reseñas de la comunidad (${uiState.reviews.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    if (uiState.reviews.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sé el primero en hacer una reseña sobre este partido",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        items(uiState.reviews) { review ->
                            val isFollowing = uiState.followingUserIds.contains(review.userId)
                            ReviewCardItem(
                                review = review,
                                currentUserId = uiState.currentUserId,
                                isFollowing = isFollowing,
                                onToggleFollow = {
                                    viewModel.toggleFollowUser(review.userId, review.username)
                                },
                                onUserClick = {
                                    onUserClick(review.userId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewForm(
    initialRating: Int,
    initialText: String,
    isSubmitting: Boolean,
    onSave: (Int, String) -> Unit,
    onCancel: (() -> Unit)? = null
) {
    var rating by remember { mutableStateOf(initialRating) }
    var text by remember { mutableStateOf(initialText) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Puntuación:",
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (1..5).forEach { star ->
                    Text(
                        text = if (star <= rating) "★" else "☆",
                        fontSize = 28.sp,
                        color = if (star <= rating) MaterialTheme.colorScheme.primary else Color.Gray,
                        modifier = Modifier.clickable { rating = star }
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$rating/5",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { if (it.length <= 500) text = it },
                label = { Text("Escribe tu reseña (máx 500 caract.)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (onCancel != null) {
                    TextButton(onClick = onCancel, enabled = !isSubmitting) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Button(
                    onClick = { onSave(rating, text) },
                    enabled = !isSubmitting
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Publicar Reseña")
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewCardItem(
    review: Review,
    currentUserId: String,
    isFollowing: Boolean,
    onToggleFollow: () -> Unit,
    onUserClick: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.username.ifBlank { "Usuario" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onUserClick() }
                )
                Text(
                    text = "⭐ ${review.rating}/5",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (review.text.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = review.text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Follow button at bottom right (only for other users)
            if (review.userId.isNotBlank() && review.userId != currentUserId) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onToggleFollow) {
                        Icon(
                            imageVector = if (isFollowing) Icons.Default.Check else Icons.Default.PersonAdd,
                            contentDescription = if (isFollowing) "Siguiendo" else "Seguir",
                            tint = if (isFollowing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isFollowing) "Siguiendo" else "Seguir",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isFollowing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
