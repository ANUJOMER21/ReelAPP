package com.Vginfotech.reelapp.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.Vginfotech.reelapp.R

@Composable
fun WalletScreen(navController: NavController) {
    WalletPage(balance = 1234.56, sampleTransactions) { }

}data class Transaction(
    val icon: Int,
    val description: String,
    val date: String,
    val amount: Double,
    val isCredit: Boolean
)

@Composable
fun WalletPage(
    balance: Double,
    transactions: List<Transaction>,
    onWithdrawClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            // Stylish Withdraw Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onWithdrawClick,
                    modifier = Modifier
                        .fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF651FFF)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(text = "Withdraw Balance", style = MaterialTheme.typography.button, color = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Improved Balance Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .aspectRatio(2.5f) // Creates a height based on aspect ratio
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF7C4DFF), Color(0xFF651FFF))
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Main Balance",
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Rs.${String.format("%.2f", balance)}",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }

            // Transaction Header
            Text(
                text = "Recent Transactions",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold)
            )

            // Stylish Transaction List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circular Icon with Background
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFD5C4FF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = transaction.icon),
                contentDescription = transaction.description,

                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = transaction.date,
                style = MaterialTheme.typography.caption.copy(color = Color.Gray)
            )
        }
        Text(
            text = (if (transaction.isCredit) "+" else "-") + "Rs.${String.format("%.2f", transaction.amount)}",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.Bold,
                color = if (transaction.isCredit) Color(0xFF651FFF) else Color.Red
            )
        )
    }
}
val sampleTransactions = listOf(
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Grocery Shopping",
        date = "2024-11-20",
        amount = 150.50,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Salary",
        date = "2024-11-15",
        amount = 3000.00,
        isCredit = true
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Coffee with Friends",
        date = "2024-11-18",
        amount = 20.00,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Monthly Rent",
        date = "2024-11-01",
        amount = 1200.00,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Gift from John",
        date = "2024-11-19",
        amount = 200.00,
        isCredit = true
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Grocery Shopping",
        date = "2024-11-20",
        amount = 150.50,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Salary",
        date = "2024-11-15",
        amount = 3000.00,
        isCredit = true
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Coffee with Friends",
        date = "2024-11-18",
        amount = 20.00,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Monthly Rent",
        date = "2024-11-01",
        amount = 1200.00,
        isCredit = false
    ),
    Transaction(
        icon = R.drawable.coin_stack_money_svgrepo_com, // Replace with your drawable resource
        description = "Gift from John",
        date = "2024-11-19",
        amount = 200.00,
        isCredit = true
    )
)
@Preview
@Composable
fun WalletPagePreview(){
    WalletPage(
        balance = 1234.56,
        transactions = sampleTransactions
    ) { }
}