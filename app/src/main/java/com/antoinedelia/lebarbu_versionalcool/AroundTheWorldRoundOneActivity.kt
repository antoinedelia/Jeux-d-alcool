package com.antoinedelia.lebarbu_versionalcool

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.Toast
import com.squareup.picasso.Picasso
import androidx.core.view.isInvisible

class AroundTheWorldRoundOneActivity : androidx.appcompat.app.AppCompatActivity() {
    private var deck: Deck? = null
    private var card: com.antoinedelia.lebarbu_versionalcool.Card? = null
    private var listPlayers: java.util.ArrayList<Player?>? = java.util.ArrayList<Player?>()
    private var numberPlayers = 0
    private var numberActualPlayer = 0
    private var round = 0
    private var lastClickTime: kotlin.Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.around_the_world_round_one)

        val linearLayoutCard: LinearLayout? = findViewById<LinearLayout?>(R.id.containerImageCard)
        val linearLayoutRedOrBlack: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageRedOrBlack)
        val linearLayoutMoreOrLess: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageMoreOrLess)
        val linearLayoutBetweenOrOutside: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageBetweenOrOutside)
        val linearLayoutSameOrDifferent: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSameOrDifferent)
        val linearLayoutSuitChoice: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSuitChoice)

        if (linearLayoutCard != null) linearLayoutCard.visibility = android.view.View.INVISIBLE

        val intent: Intent = getIntent()
        listPlayers = intent.getParcelableArrayListExtra<Player?>("listPlayers")
        numberPlayers = if (listPlayers != null) listPlayers!!.size else 0
        for (i in 0..<numberPlayers) listPlayers!![i]!!.specialTrait = java.util.ArrayList<Player.Trait?>(2)

        for (i in 0..<numberPlayers) {
            for (j in 0..4) {
                listPlayers!![i]!!.getCards().add(
                    j,
                    com.antoinedelia.lebarbu_versionalcool.Card(null, "unknown_card", null, null)
                )
            }
        }

        deck = Deck("AroundTheWorldRoundOne", this)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar

        // Enable the Up button
        ab?.setDisplayHomeAsUpEnabled(true)

        val imageViewCard = findViewById<android.widget.ImageView?>(R.id.imageViewCarte)

        if (numberPlayers != 0) {
            val nameActualPlayer: TextView? = findViewById<TextView?>(R.id.nameActualPlayer)
            val actualPlayer =
                getResources().getString(R.string.currentPlayer) + " " + listPlayers!!.get(
                    numberActualPlayer
                )
            nameActualPlayer?.text = actualPlayer
        }

        //Click on card
        imageViewCard?.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (imageViewCard.isInvisible) return
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    val cardsLeft = deck!!.remainingCards
                    if (cardsLeft > 0) {
                        numberActualPlayer++
                        if (numberActualPlayer == numberPlayers) {
                            numberActualPlayer = 0
                            if (round < 4) {
                                round++
                                changeRound()
                            } else {
                                val builder =
                                    androidx.appcompat.app.AlertDialog.Builder(this@AroundTheWorldRoundOneActivity)
                                builder.setIcon(R.drawable.around_the_world_round_one)
                                builder.setMessage(getResources().getString(R.string.gameOver))
                                    .setTitle(getResources().getString(R.string.gameOver))

                                builder.setPositiveButton(
                                    getResources().getString(R.string.ok),
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface, which: Int) {
                                            dialog.dismiss()
                                            finish()
                                        }
                                    })
                                builder.setOnDismissListener { dialog ->
                                    dialog.dismiss()
                                    finish()
                                }
                                val dialog: android.app.Dialog = builder.create()
                                dialog.show()
                            }
                        }

                        changeViews()

                        if (numberPlayers > 0) {
                            val nameActualPlayer: TextView? =
                                findViewById<TextView?>(R.id.nameActualPlayer)
                            val actualPlayer =
                                getResources().getString(R.string.currentPlayer) + " " + listPlayers!!.get(
                                    numberActualPlayer
                                )
                            nameActualPlayer?.text = actualPlayer
                        }
                        refreshCards()
                    } else {
                        newGame()
                    }
                }
            }
        )

        val imageViewRed = findViewById<android.widget.ImageView?>(R.id.imageViewRed)
        //Click on red
        if (imageViewRed != null) imageViewRed.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.path, "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!![numberActualPlayer]!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewRed.isInvisible) return
                    linearLayoutCard?.visibility = android.view.View.VISIBLE
                    linearLayoutRedOrBlack?.visibility = android.view.View.INVISIBLE
                    checkSips(getResources().getString(R.string.red))
                }
            }
        )

        val imageViewBlack = findViewById<android.widget.ImageView?>(R.id.imageViewBlack)
        //Click on black
        if (imageViewBlack != null) imageViewBlack.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewBlack.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.black))
                }
            }
        )

        val imageViewLess = findViewById<android.widget.ImageView?>(R.id.imageViewLess)
        //Click on less
        if (imageViewLess != null) imageViewLess.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewLess.getVisibility() == android.view.View.INVISIBLE) return
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.less))
                }
            }
        )

        val imageViewMore = findViewById<android.widget.ImageView?>(R.id.imageViewMore)
        //Click on more
        if (imageViewMore != null) imageViewMore.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewMore.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.more))
                }
            }
        )

        val imageViewEquals1 = findViewById<android.widget.ImageView?>(R.id.imageViewEquals1)
        //Click on more
        if (imageViewEquals1 != null) imageViewEquals1.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewEquals1.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.equals))
                }
            }
        )

        val imageViewBetween = findViewById<android.widget.ImageView?>(R.id.imageViewBetween)
        //Click on between
        if (imageViewBetween != null) imageViewBetween.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewBetween.getVisibility() == android.view.View.INVISIBLE) return
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                        android.view.View.INVISIBLE
                    )
                    checkSips(getResources().getString(R.string.between))
                }
            }
        )

        val imageViewOutside = findViewById<android.widget.ImageView?>(R.id.imageViewOutside)
        //Click on outside
        if (imageViewOutside != null) imageViewOutside.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewOutside.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                        android.view.View.INVISIBLE
                    )
                    checkSips(getResources().getString(R.string.outside))
                }
            }
        )

        val imageViewEquals2 = findViewById<android.widget.ImageView?>(R.id.imageViewEquals2)
        //Click on outside
        if (imageViewEquals2 != null) imageViewEquals2.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewEquals2.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                        android.view.View.INVISIBLE
                    )
                    checkSips(getResources().getString(R.string.equals))
                }
            }
        )

        val imageViewSame = findViewById<android.widget.ImageView?>(R.id.imageViewSame)
        //Click on same
        if (imageViewSame != null) imageViewSame.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewSame.getVisibility() == android.view.View.INVISIBLE) return
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(
                        android.view.View.INVISIBLE
                    )
                    checkSips(getResources().getString(R.string.same))
                }
            }
        )

        val imageViewDifferent = findViewById<android.widget.ImageView?>(R.id.imageViewDifferent)
        //Click on different
        if (imageViewDifferent != null) imageViewDifferent.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewDifferent.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(
                        android.view.View.INVISIBLE
                    )
                    checkSips(getResources().getString(R.string.different))
                }
            }
        )

        val imageViewHearts = findViewById<android.widget.ImageView?>(R.id.imageViewHearts)
        //Click on hearts
        if (imageViewHearts != null) imageViewHearts.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewHearts.getVisibility() == android.view.View.INVISIBLE) return
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.hearts))
                }
            }
        )

        val imageViewSpades = findViewById<android.widget.ImageView?>(R.id.imageViewSpades)
        //Click on spades
        if (imageViewSpades != null) imageViewSpades.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewSpades.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.spades))
                }
            }
        )

        val imageViewDiamonds = findViewById<android.widget.ImageView?>(R.id.imageViewDiamonds)
        //Click on diamonds
        if (imageViewDiamonds != null) imageViewDiamonds.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewDiamonds.getVisibility() == android.view.View.INVISIBLE) return
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.diamonds))
                }
            }
        )

        val imageViewClubs = findViewById<android.widget.ImageView?>(R.id.imageViewClubs)
        //Click on clubs
        if (imageViewClubs != null) imageViewClubs.setOnClickListener(
            object : android.view.View.OnClickListener {
                override fun onClick(v: android.view.View?) {
                    if (android.os.SystemClock.elapsedRealtime() - lastClickTime < AroundTheWorldRoundOneActivity.Companion.DELAY_TIME) {
                        return
                    }
                    lastClickTime = android.os.SystemClock.elapsedRealtime()
                    card = deck!!.getNextCard()
                    val resourceId =
                        this@AroundTheWorldRoundOneActivity.getResources().getIdentifier(
                            card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool"
                        )
                    if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                        .load(resourceId).into(imageViewCard)
                    listPlayers!!.get(numberActualPlayer)!!.getCards().set(round, card)
                    refreshCards()
                    if (imageViewClubs.getVisibility() == android.view.View.INVISIBLE) {
                        return
                    }
                    if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.VISIBLE)
                    if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.INVISIBLE)
                    checkSips(getResources().getString(R.string.clubs))
                }
            }
        )
    }

    fun changeRound() {
        val linearLayoutRedOrBlack: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageRedOrBlack)
        val linearLayoutMoreOrLess: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageMoreOrLess)
        val linearLayoutBetweenOrOutside: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageBetweenOrOutside)
        val linearLayoutSameOrDifferent: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSameOrDifferent)
        val linearLayoutSuitChoice: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSuitChoice)

        val textViewQuestionRound: TextView? = findViewById<TextView?>(R.id.questionRound)

        when (round) {
            1 -> {
                if (linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(android.view.View.INVISIBLE)
                if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.VISIBLE)
                if (textViewQuestionRound != null) textViewQuestionRound.setText(
                    getResources().getString(
                        R.string.aroundTheWorldRoundOnePartTwo
                    )
                )
                val imageViewRed = findViewById<android.widget.ImageView?>(R.id.imageViewRed)
                val imageViewBlack = findViewById<android.widget.ImageView?>(R.id.imageViewBlack)
                if (imageViewRed != null) imageViewRed.setOnClickListener(null)
                if (imageViewBlack != null) imageViewBlack.setOnClickListener(null)
                return
            }

            2 -> {
                if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.INVISIBLE)
                if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                    android.view.View.VISIBLE
                )
                if (textViewQuestionRound != null) textViewQuestionRound.setText(
                    getResources().getString(
                        R.string.aroundTheWorldRoundOnePartThree
                    )
                )
                val imageViewLess = findViewById<android.widget.ImageView?>(R.id.imageViewLess)
                val imageViewMore = findViewById<android.widget.ImageView?>(R.id.imageViewMore)
                if (imageViewLess != null) imageViewLess.setOnClickListener(null)
                if (imageViewMore != null) imageViewMore.setOnClickListener(null)
                return
            }

            3 -> {
                if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                    android.view.View.INVISIBLE
                )
                if (linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(
                    android.view.View.VISIBLE
                )
                if (textViewQuestionRound != null) textViewQuestionRound.setText(
                    getResources().getString(
                        R.string.aroundTheWorldRoundOnePartFour
                    )
                )
                val imageViewBetween =
                    findViewById<android.widget.ImageView?>(R.id.imageViewBetween)
                val imageViewOutside =
                    findViewById<android.widget.ImageView?>(R.id.imageViewOutside)
                if (imageViewBetween != null) imageViewBetween.setOnClickListener(null)
                if (imageViewOutside != null) imageViewOutside.setOnClickListener(null)
                return
            }

            4 -> {
                if (linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(
                    android.view.View.INVISIBLE
                )
                if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.VISIBLE)
                if (textViewQuestionRound != null) textViewQuestionRound.setText(
                    getResources().getString(
                        R.string.aroundTheWorldRoundOnePartFive
                    )
                )
                val imageViewSame = findViewById<android.widget.ImageView?>(R.id.imageViewSame)
                val imageViewDifferent =
                    findViewById<android.widget.ImageView?>(R.id.imageViewDifferent)
                if (imageViewSame != null) imageViewSame.setOnClickListener(null)
                if (imageViewDifferent != null) imageViewDifferent.setOnClickListener(null)
            }
        }
    }

    fun refreshCards() {
        val imageViewCardOne = findViewById<android.widget.ImageView?>(R.id.cardOne)
        val imageViewCardTwo = findViewById<android.widget.ImageView?>(R.id.cardTwo)
        val imageViewCardThree = findViewById<android.widget.ImageView?>(R.id.cardThree)
        val imageViewCardFour = findViewById<android.widget.ImageView?>(R.id.cardFour)
        val imageViewCardFive = findViewById<android.widget.ImageView?>(R.id.cardFive)

        if (round >= 0) {
            val resourceId1 = this.getResources().getIdentifier(
                "thumbnail_" + listPlayers!!.get(numberActualPlayer)!!
                    .getCards().get(0).getPath(),
                "drawable",
                "com.antoinedelia.lebarbu_versionalcool"
            )
            if (imageViewCardOne != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                .load(resourceId1).into(imageViewCardOne)
        }
        if (round >= 1) {
            val resourceId2 = this.getResources().getIdentifier(
                "thumbnail_" + listPlayers!!.get(numberActualPlayer)!!
                    .getCards().get(1).getPath(),
                "drawable",
                "com.antoinedelia.lebarbu_versionalcool"
            )
            if (imageViewCardTwo != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                .load(resourceId2).into(imageViewCardTwo)
        }
        if (round >= 2) {
            val resourceId3 = this.getResources().getIdentifier(
                "thumbnail_" + listPlayers!!.get(numberActualPlayer)!!
                    .getCards().get(2).getPath(),
                "drawable",
                "com.antoinedelia.lebarbu_versionalcool"
            )
            if (imageViewCardThree != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                .load(resourceId3).into(imageViewCardThree)
        }
        if (round >= 3) {
            val resourceId4 = this.getResources().getIdentifier(
                "thumbnail_" + listPlayers!!.get(numberActualPlayer)!!
                    .getCards().get(3).getPath(),
                "drawable",
                "com.antoinedelia.lebarbu_versionalcool"
            )
            if (imageViewCardFour != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                .load(resourceId4).into(imageViewCardFour)
        }
        if (round >= 4) {
            val resourceId5 = this.getResources().getIdentifier(
                "thumbnail_" + listPlayers!!.get(numberActualPlayer)!!
                    .getCards().get(4).getPath(),
                "drawable",
                "com.antoinedelia.lebarbu_versionalcool"
            )
            if (imageViewCardFive != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
                .load(resourceId5).into(imageViewCardFive)
        }
    }


    override fun onCreateOptionsMenu(menu: android.view.Menu?): kotlin.Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_around_the_world_round_one, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): kotlin.Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                val intent: Intent = Intent()
                //We send back the list of the players
                intent.putParcelableArrayListExtra("listPlayers", listPlayers)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            R.id.action_infoPlayers -> if (numberPlayers != 0) {
                //We show the information about the players
                val builder = androidx.appcompat.app.AlertDialog.Builder(this)
                builder.setIcon(R.drawable.around_the_world_round_one)
                builder.setTitle(getResources().getString(R.string.action_players))

                val playersWithInfo: kotlin.collections.MutableList<kotlin.String?> =
                    java.util.ArrayList<kotlin.String?>()
                var i = 0
                while (i < listPlayers!!.size) {
                    val textSip =
                        getResources().getString(R.string.sip) + (if (listPlayers!!.get(i)!!
                                .getNumberSips() > 1
                        ) "s" else "")
                    val textToDisplay = listPlayers!!.get(i)!!
                        .getName() + " " + getResources().getString(R.string.drank) + " " + listPlayers!!.get(
                        i
                    )!!
                        .getNumberSips() + " " + textSip
                    playersWithInfo.add(textToDisplay.trim { it <= ' ' })
                    i++
                }
                val playersList = android.widget.ListView(this)
                val arrayAdapter: ArrayAdapter<String?> = ArrayAdapter<kotlin.String?>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    playersWithInfo
                )
                playersList.setAdapter(arrayAdapter)

                builder.setView(playersList)
                builder.setPositiveButton(
                    getResources().getString(R.string.ok),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            dialog.dismiss()
                        }
                    })
                val dialog: android.app.Dialog = builder.create()
                dialog.show()
            } else {
                Toast.makeText(
                    this@AroundTheWorldRoundOneActivity,
                    getResources().getString(R.string.noPlayer),
                    Toast.LENGTH_SHORT
                ).show()
            }

            R.id.action_help -> {
                val intentHelp: Intent = Intent(
                    this@AroundTheWorldRoundOneActivity,
                    AroundTheWorldRoundOneHelpActivity::class.java
                )
                intentHelp.putParcelableArrayListExtra("listPlayers", listPlayers)
                startActivityForResult(intentHelp, 0)
            }
        }
        return true
    }

    private fun newGame() {
        deck = Deck("AroundTheWorldRoundOne", this)
        card = deck!!.getNextCard()
        numberPlayers = listPlayers!!.size
        numberActualPlayer++
        if (numberActualPlayer == numberPlayers) {
            numberActualPlayer = 0
            if (round < 4) {
                round++
                changeRound()
            } else {
                val builder =
                    androidx.appcompat.app.AlertDialog.Builder(this@AroundTheWorldRoundOneActivity)
                builder.setIcon(R.drawable.around_the_world_round_one)
                builder.setMessage(getResources().getString(R.string.gameOver))
                    .setTitle(getResources().getString(R.string.gameOver))
                builder.setPositiveButton(
                    getResources().getString(R.string.ok),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            dialog.dismiss()
                            finish()
                        }
                    })
                builder.setOnDismissListener(object : DialogInterface.OnDismissListener {
                    override fun onDismiss(dialog: DialogInterface) {
                        dialog.dismiss()
                        finish()
                    }
                })
                val dialog: android.app.Dialog = builder.create()
                dialog.show()
            }
        }
        changeViews()
        val imageViewCard = findViewById<android.widget.ImageView?>(R.id.imageViewCarte)
        val resourceId = this.getResources()
            .getIdentifier(card!!.getPath(), "drawable", "com.antoinedelia.lebarbu_versionalcool")
        if (imageViewCard != null) Picasso.with(this@AroundTheWorldRoundOneActivity)
            .load(resourceId).into(imageViewCard)
        if (numberPlayers != 0) {
            val nameActualPlayer: TextView? = findViewById<TextView?>(R.id.nameActualPlayer)
            val actualPlayer =
                getResources().getString(R.string.currentPlayer) + " " + listPlayers!!.get(
                    numberActualPlayer
                )
            if (nameActualPlayer != null) nameActualPlayer.setText(actualPlayer)
            listPlayers!!.get(numberActualPlayer)!!
                .setNumberSips(listPlayers!!.get(numberActualPlayer)!!.getNumberSips() + 1)
        }
        refreshCards()
    }

    override fun onBackPressed() {
        val intent: Intent = Intent()
        //We send back the list of the players
        intent.putParcelableArrayListExtra("listPlayers", listPlayers)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun checkSips(choice: kotlin.String) {
        val textViewQuestionRound: TextView? = findViewById<TextView?>(R.id.questionRound)
        val textToDisplay: kotlin.String?
        var win = false
        var isDouble = false
        if (numberPlayers > 0) {
            when (round) {
                0 -> if (card!!.getSuit() == Deck.SuitCards.HEARTS || card!!.getSuit() == Deck.SuitCards.DIAMONDS) {
                    if (choice == getResources().getString(R.string.red)) win = true
                } else {
                    if (choice == getResources().getString(R.string.black)) win = true
                }

                1 -> if (card!!.getName().getNumVal() > listPlayers!!.get(numberActualPlayer)!!
                        .getCards().get(0).getName().getNumVal()
                ) {
                    if (choice == getResources().getString(R.string.more)) win = true
                } else if (card!!.getName().getNumVal() < listPlayers!!.get(numberActualPlayer)!!
                        .getCards().get(0).getName().getNumVal()
                ) {
                    if (choice == getResources().getString(R.string.less)) win = true
                } else if (card!!.getName().getNumVal() == listPlayers!!.get(numberActualPlayer)!!
                        .getCards().get(0).getName().getNumVal()
                ) {
                    if (choice == getResources().getString(R.string.equals)) win = true
                    isDouble = true
                }

                2 -> {
                    val lowestCard =
                        if (listPlayers!!.get(numberActualPlayer)!!.getCards().get(0).getName()
                                .getNumVal() < listPlayers!!.get(numberActualPlayer)!!
                                .getCards().get(1).getName().getNumVal()
                        ) listPlayers!!.get(numberActualPlayer)!!
                            .getCards().get(0) else listPlayers!!.get(numberActualPlayer)!!
                            .getCards().get(1)
                    val highestCard =
                        if (listPlayers!!.get(numberActualPlayer)!!.getCards().get(0).getName()
                                .getNumVal() > listPlayers!!.get(numberActualPlayer)!!
                                .getCards().get(1).getName().getNumVal()
                        ) listPlayers!!.get(numberActualPlayer)!!
                            .getCards().get(0) else listPlayers!!.get(numberActualPlayer)!!
                            .getCards().get(1)
                    if (card!!.getName().getNumVal() < lowestCard.getName()
                            .getNumVal() || card!!.getName().getNumVal() > highestCard.getName()
                            .getNumVal()
                    ) {
                        if (choice == getResources().getString(R.string.outside)) win = true
                    } else if (card!!.getName().getNumVal() > lowestCard.getName()
                            .getNumVal() && card!!.getName().getNumVal() < highestCard.getName()
                            .getNumVal()
                    ) {
                        if (choice == getResources().getString(R.string.between)) win = true
                    } else if (card!!.getName().getNumVal() == lowestCard.getName()
                            .getNumVal() || card!!.getName().getNumVal() == highestCard.getName()
                            .getNumVal()
                    ) {
                        if (choice == getResources().getString(R.string.equals)) win = true
                        isDouble = true
                    }
                }

                3 -> {
                    val listSuit: kotlin.collections.MutableList<Deck.SuitCards?> =
                        java.util.ArrayList<Deck.SuitCards?>()
                    for (card in listPlayers!!.get(numberActualPlayer)!!.getCards()) {
                        listSuit.add(card.getSuit())
                    }
                    if (listSuit.contains(card!!.getSuit())) {
                        if (choice == getResources().getString(R.string.same)) win = true
                    } else {
                        if (choice == getResources().getString(R.string.different)) win = true
                    }
                }

                4 -> if (card!!.getSuit() == Deck.SuitCards.HEARTS) {
                    if (choice == getResources().getString(R.string.hearts)) win = true
                } else if (card!!.getSuit() == Deck.SuitCards.DIAMONDS) {
                    if (choice == getResources().getString(R.string.diamonds)) win = true
                } else if (card!!.getSuit() == Deck.SuitCards.SPADES) {
                    if (choice == getResources().getString(R.string.spades)) win = true
                } else if (card!!.getSuit() == Deck.SuitCards.CLUBS) {
                    if (choice == getResources().getString(R.string.clubs)) win = true
                }
            }
            if (!win) {
                textToDisplay =
                    getResources().getString(R.string.youDrink) + " " + ((round + 1) * (if (isDouble) 2 else 1)) + " " + getResources().getString(
                        R.string.sip
                    ) + (if (round < 1) "" else "s")
                listPlayers!!.get(numberActualPlayer)!!
                    .setNumberSips(
                        listPlayers!!.get(numberActualPlayer)!!
                            .getNumberSips() + ((round + 1) * (if (isDouble) 2 else 1))
                    )
            } else textToDisplay =
                getResources().getString(R.string.youGive) + " " + ((round + 1) * (if (isDouble) 2 else 1)) + " " + getResources().getString(
                    R.string.sip
                ) + (if (round < 1) "" else "s")

            if (textViewQuestionRound != null) textViewQuestionRound.setText(textToDisplay)
        }
    }

    fun changeViews() {
        val linearLayoutCard: LinearLayout? = findViewById<LinearLayout?>(R.id.containerImageCard)
        val linearLayoutRedOrBlack: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageRedOrBlack)
        val linearLayoutMoreOrLess: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageMoreOrLess)
        val linearLayoutBetweenOrOutside: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageBetweenOrOutside)
        val linearLayoutSameOrDifferent: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSameOrDifferent)
        val linearLayoutSuitChoice: LinearLayout? =
            findViewById<LinearLayout?>(R.id.containerImageSuitChoice)
        val textViewQuestionRound: TextView? = findViewById<TextView?>(R.id.questionRound)
        if (round == 0) {
            if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.INVISIBLE)
            if (linearLayoutRedOrBlack != null) linearLayoutRedOrBlack.setVisibility(android.view.View.VISIBLE)
            if (textViewQuestionRound != null) textViewQuestionRound.setText(
                getResources().getString(
                    R.string.aroundTheWorldRoundOnePartOne
                )
            )
        }
        if (round == 1) {
            if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.INVISIBLE)
            if (linearLayoutMoreOrLess != null) linearLayoutMoreOrLess.setVisibility(android.view.View.VISIBLE)
            if (textViewQuestionRound != null) textViewQuestionRound.setText(
                getResources().getString(
                    R.string.aroundTheWorldRoundOnePartTwo
                )
            )
        }
        if (round == 2) {
            if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.INVISIBLE)
            if (linearLayoutBetweenOrOutside != null) linearLayoutBetweenOrOutside.setVisibility(
                android.view.View.VISIBLE
            )
            if (textViewQuestionRound != null) textViewQuestionRound.setText(
                getResources().getString(
                    R.string.aroundTheWorldRoundOnePartThree
                )
            )
        }
        if (round == 3) {
            if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.INVISIBLE)
            if (linearLayoutSameOrDifferent != null) linearLayoutSameOrDifferent.setVisibility(
                android.view.View.VISIBLE
            )
            if (textViewQuestionRound != null) textViewQuestionRound.setText(
                getResources().getString(
                    R.string.aroundTheWorldRoundOnePartFour
                )
            )
        }
        if (round == 4) {
            if (linearLayoutCard != null) linearLayoutCard.setVisibility(android.view.View.INVISIBLE)
            if (linearLayoutSuitChoice != null) linearLayoutSuitChoice.setVisibility(android.view.View.VISIBLE)
            if (textViewQuestionRound != null) textViewQuestionRound.setText(
                getResources().getString(
                    R.string.aroundTheWorldRoundOnePartFive
                )
            )
        }
    }

    companion object {
        private const val DELAY_TIME = 300
    }
}
