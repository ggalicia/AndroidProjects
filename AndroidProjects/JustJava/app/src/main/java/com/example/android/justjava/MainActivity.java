package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    //int price = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price;

        //find text box
        //return text in box
        EditText textName = (EditText)findViewById(R.id.name_description_view);
        String userName = textName.getText().toString();

        //find check box
        //check if checkbox is checked
        CheckBox whippedCreamBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamBox.isChecked();

        //find check box
        //check if checkbox is checked
        CheckBox chocolateBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateBox.isChecked();

        //calculate base price
        price = calculatePrice(hasWhippedCream, hasChocolate);

        String orderSummary = createOrderSummary(userName, price, hasWhippedCream, hasChocolate);
        //String subject = "Just Java order for " + userName;

        //send order to e-mail application
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_email_subject, userName));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(orderSummary);
    }
    /**
     * Create summary of the order.
     * @param userName is user name
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String userName, int price, boolean hasWhippedCream, boolean hasChocolate){

        String priceMessage = getString(R.string.customer_name, userName);
        priceMessage += "\n" + getString(R.string.add_whipped_cream, (hasWhippedCream ? getString(R.string.yes) : getString(R.string.no)));
        priceMessage += "\n" + getString(R.string.add_chocolate, (hasChocolate ? getString(R.string.yes) : getString(R.string.no)));
        priceMessage += "\n" + getString(R.string.order_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate)
    {
        int basePrice = 5;

        //add $1 if user wants whipped cream
        if(hasWhippedCream){
            basePrice = basePrice + 1;
        }

        //add $2 if user wants chocolate
        if(hasChocolate){
            basePrice = basePrice + 2;
        }

        //calculate the total order price by multiplying by quantity
        return quantity*basePrice;


        //another way to check for all cases
        /**if((hasWhippedCream == true) && (hasChocolate == true))
        {
            basePrice = basePrice + 2 + 1;
            return basePrice * quantity;

        }
        else if(hasWhippedCream == true)
        {
            basePrice = basePrice + 1;
            return basePrice * quantity;
        }
        else if (hasChocolate == true)
        {
            basePrice = basePrice + 2;
            return basePrice * quantity;
        }
        else
        {
           return basePrice;
        }
        **/
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    /**private void calculatePrice(int quantity) {

        int price = quantity * 5;
    }
    */

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     * @param priceOfCup is the price per cup of coffee
     */
    /**private void calculatePrice(int quantity, int priceOfCup) {
        int price = quantity * priceOfCup;
    }
    */

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {

        if(quantity == 100)
        {
            Toast.makeText(this, getString(R.string.too_many_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

        //this is another way to do it
        /*//method to display that user cannot have more than 100 cups of coffee
        quantity = quantity + 1;
        if(quantity > 100)
        {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have more than 100 cups of coffee.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity = 100;
            return;
        }
        else
        {
            displayQuantity(quantity);
        }
        */

    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {

        if(quantity == 0)
        {
            Toast.makeText(this, getString(R.string.too_few_coffees), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity -1;
        displayQuantity(quantity);


        //this is another way to do it
        /*quantity = quantity - 1;
        //method to display that user cannot have less than 1 cup of coffee
        if(quantity < 1)
        {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 cup of coffee.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            quantity = 1;
            return;
        }
        else
        {
            displayQuantity(quantity);
        }
        */

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantityTotal) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantityTotal);
    }

    /**
     * This method displays the given price on the screen.
     */
    /**private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    */

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}