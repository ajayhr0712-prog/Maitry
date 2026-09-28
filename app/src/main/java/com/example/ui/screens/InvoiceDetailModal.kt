package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.InvoiceEntity
import com.example.ui.theme.CreamSurface
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.SurfaceVariantWarm
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedGreen
import com.example.ui.theme.VerifiedGreenContainer

@Composable
fun InvoiceDetailDialog(
    invoice: InvoiceEntity,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Top Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = DeepTeal,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Receipt, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("GST TAX INVOICE", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = DeepTeal)
                            Text("Original for Recipient", fontSize = 10.sp, color = TextSecondaryMuted)
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondaryMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = Color(0xFFE2E9EA))
                Spacer(modifier = Modifier.height(12.dp))

                // Supplier & Customer Details
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("SUPPLIER / ISSUER:", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = DeepTeal)
                        Text("Maitry Digital Trust Ltd", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TextPrimaryDark)
                        Text("GSTIN: 07AAECM4812K1Z9", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TextSecondaryMuted)
                        Text("SAC Code: 998431 (Online Social & Dating Services)", fontSize = 10.sp, color = TextSecondaryMuted)
                        Text("New Delhi - 110001", fontSize = 10.sp, color = TextSecondaryMuted)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text("INVOICE DETAILS:", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = DeepTeal)
                        Text("Invoice #: ${invoice.invoiceNumber}", fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = TextPrimaryDark)
                        Text("Date: ${invoice.issueDate}", fontSize = 10.sp, color = TextSecondaryMuted)
                        Text("Place of Supply: ${invoice.customerState}", fontSize = 10.sp, color = TextSecondaryMuted)
                        Text("Payment: ${invoice.paymentMethod}", fontSize = 10.sp, color = TextSecondaryMuted)
                        Text("Gateway: Razorpay (${com.example.BuildConfig.RAZORPAY_KEY_ID.ifEmpty { "rzp_live_ThZb8wghApMABI" }})", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = DeepTeal)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = Color(0xFFE2E9EA))
                Spacer(modifier = Modifier.height(12.dp))

                // Customer info
                Column {
                    Text("BILLED TO:", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = DeepTeal)
                    Text(invoice.customerName, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TextPrimaryDark)
                    Text("Phone: ${invoice.customerPhone} • ${invoice.customerCity}, ${invoice.customerState}", fontSize = 11.sp, color = TextSecondaryMuted)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Line Items Table
                Surface(
                    color = SurfaceVariantWarm,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Description", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DeepTeal)
                            Text("Amount (INR)", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DeepTeal)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(invoice.planName, fontSize = 11.sp, color = TextPrimaryDark)
                            Text("₹${invoice.baseAmount}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextPrimaryDark)
                        }
                        if (invoice.cgstAmount > 0) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("CGST (9%)", fontSize = 10.sp, color = TextSecondaryMuted)
                                Text("₹${invoice.cgstAmount}", fontSize = 10.sp, color = TextSecondaryMuted)
                            }
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("SGST (9%)", fontSize = 10.sp, color = TextSecondaryMuted)
                                Text("₹${invoice.sgstAmount}", fontSize = 10.sp, color = TextSecondaryMuted)
                            }
                        } else {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("IGST (18%)", fontSize = 10.sp, color = TextSecondaryMuted)
                                Text("₹${invoice.igstAmount}", fontSize = 10.sp, color = TextSecondaryMuted)
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Divider(color = Color(0xFFD6DFE0))
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total (GST Inclusive)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = DeepTeal)
                            Text("₹${invoice.totalAmount}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp, color = DeepTeal)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = VerifiedGreenContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = VerifiedGreen, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Status: PAID • Razorpay Ref: ${invoice.razorpayPaymentId}",
                            color = VerifiedGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share")
                    }

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Download PDF")
                    }
                }
            }
        }
    }
}
