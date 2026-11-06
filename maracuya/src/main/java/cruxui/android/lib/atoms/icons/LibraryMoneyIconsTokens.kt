package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryMoneyIconsTokens declara los IconToken de una categoría Library.
 *
 * Justificación:
 * Es la única declaración de los iconos de esta categoría. LibraryIconTokens la incorpora al tema;
 * token-exporter descubre todos sus getters para generar roles, referencias XML y consultas sin duplicarlos.
 *
 * Uso:
 * API pública de configuración. Personalizar con copy(nombreDelIcono = otroToken) y entregar la categoría
 * al campo correspondiente de LibraryIconTokens; LibraryThemes consume esa colección.
 * El host normalmente lee library.icons.<grupo>.<icono> desde LibraryTheme.current o una receta.
 * Para ampliar el DS agregar un val IconToken al constructor y su valor en el catálogo del companion.
 * resourceName debe nombrar un drawable existente en res/drawable, sin extensión ni prefijo @drawable/.
 * Recompilar genera LibraryIconRole, LibraryXmlReferences.icons y ?attr/libraryIcon<Grupo><Icono>.
 * Cambiar el recurso del token existente actualiza ambos consumidores sin modificar el exportador.
 * Los recursos propios del host mediante filledResId son solo de ejecución, no exportables por este módulo.
 *
 * Objetivos:
 * - Mantener nombres, assets y personalizaciones en una única fuente.
 * - Evitar mapeos por icono en semantics o wrappers.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @see cruxui.android.lib.atoms.LibraryIconTokens
 */
data class LibraryMoneyIconsTokens (
    /** Token accountBalanceWallet; puede sustituirse al configurar la categoría del tema. */
    val accountBalanceWallet: IconToken,
    /** Token addCard; puede sustituirse al configurar la categoría del tema. */
    val addCard: IconToken,
    /** Token addShoppingCart; puede sustituirse al configurar la categoría del tema. */
    val addShoppingCart: IconToken,
    /** Token bitcoin; puede sustituirse al configurar la categoría del tema. */
    val bitcoin: IconToken,
    /** Token cardMembership; puede sustituirse al configurar la categoría del tema. */
    val cardMembership: IconToken,
    /** Token cardTravel; puede sustituirse al configurar la categoría del tema. */
    val cardTravel: IconToken,
    /** Token colon; puede sustituirse al configurar la categoría del tema. */
    val colon: IconToken,
    /** Token creditCard; puede sustituirse al configurar la categoría del tema. */
    val creditCard: IconToken,
    /** Token creditCardClock; puede sustituirse al configurar la categoría del tema. */
    val creditCardClock: IconToken,
    /** Token creditCardGear; puede sustituirse al configurar la categoría del tema. */
    val creditCardGear: IconToken,
    /** Token creditCardHeart; puede sustituirse al configurar la categoría del tema. */
    val creditCardHeart: IconToken,
    /** Token creditCardOff; puede sustituirse al configurar la categoría del tema. */
    val creditCardOff: IconToken,
    /** Token creditScore; puede sustituirse al configurar la categoría del tema. */
    val creditScore: IconToken,
    /** Token dollar; puede sustituirse al configurar la categoría del tema. */
    val dollar: IconToken,
    /** Token dollarCircle; puede sustituirse al configurar la categoría del tema. */
    val dollarCircle: IconToken,
    /** Token dollarExchange; puede sustituirse al configurar la categoría del tema. */
    val dollarExchange: IconToken,
    /** Token dollarOff; puede sustituirse al configurar la categoría del tema. */
    val dollarOff: IconToken,
    /** Token euro; puede sustituirse al configurar la categoría del tema. */
    val euro: IconToken,
    /** Token franc; puede sustituirse al configurar la categoría del tema. */
    val franc: IconToken,
    /** Token lira; puede sustituirse al configurar la categoría del tema. */
    val lira: IconToken,
    /** Token moneyBag; puede sustituirse al configurar la categoría del tema. */
    val moneyBag: IconToken,
    /** Token moneyRange; puede sustituirse al configurar la categoría del tema. */
    val moneyRange: IconToken,
    /** Token paymentArrowDown; puede sustituirse al configurar la categoría del tema. */
    val paymentArrowDown: IconToken,
    /** Token paymentCard; puede sustituirse al configurar la categoría del tema. */
    val paymentCard: IconToken,
    /** Token payments; puede sustituirse al configurar la categoría del tema. */
    val payments: IconToken,
    /** Token pound; puede sustituirse al configurar la categoría del tema. */
    val pound: IconToken,
    /** Token productionQuantityLimits; puede sustituirse al configurar la categoría del tema. */
    val productionQuantityLimits: IconToken,
    /** Token psychiatry; puede sustituirse al configurar la categoría del tema. */
    val psychiatry: IconToken,
    /** Token redeem; puede sustituirse al configurar la categoría del tema. */
    val redeem: IconToken,
    /** Token ruble; puede sustituirse al configurar la categoría del tema. */
    val ruble: IconToken,
    /** Token rupee; puede sustituirse al configurar la categoría del tema. */
    val rupee: IconToken,
    /** Token savings; puede sustituirse al configurar la categoría del tema. */
    val savings: IconToken,
    /** Token sendMoney; puede sustituirse al configurar la categoría del tema. */
    val sendMoney: IconToken,
    /** Token shoppingBag; puede sustituirse al configurar la categoría del tema. */
    val shoppingBag: IconToken,
    /** Token shoppingBagSpeed; puede sustituirse al configurar la categoría del tema. */
    val shoppingBagSpeed: IconToken,
    /** Token shoppingCart; puede sustituirse al configurar la categoría del tema. */
    val shoppingCart: IconToken,
    /** Token shoppingCartCheckout; puede sustituirse al configurar la categoría del tema. */
    val shoppingCartCheckout: IconToken,
    /** Token shoppingCartOff; puede sustituirse al configurar la categoría del tema. */
    val shoppingCartOff: IconToken,
    /** Token sprint; puede sustituirse al configurar la categoría del tema. */
    val sprint: IconToken,
    /** Token toll; puede sustituirse al configurar la categoría del tema. */
    val toll: IconToken,
    /** Token universalCurrency; puede sustituirse al configurar la categoría del tema. */
    val universalCurrency: IconToken,
    /** Token universalCurrencyAlt; puede sustituirse al configurar la categoría del tema. */
    val universalCurrencyAlt: IconToken,
    /** Token wallet; puede sustituirse al configurar la categoría del tema. */
    val wallet: IconToken,
    /** Token yen; puede sustituirse al configurar la categoría del tema. */
    val yen: IconToken

){
    companion object{
        /** Valores base de la categoría; fuente única de sus assets. */
        val money = LibraryMoneyIconsTokens(

            accountBalanceWallet = IconToken("ic_account_balance_wallet", resourceName = "ic_account_balance_wallet"),
            addCard = IconToken("ic_add_card", resourceName = "ic_add_card"),
            addShoppingCart = IconToken("ic_add_shopping_cart", resourceName = "ic_add_shopping_cart"),
            bitcoin = IconToken("ic_bitcoin", resourceName = "ic_bitcoin"),
            cardMembership = IconToken("ic_card_membership", resourceName = "ic_card_membership"),
            cardTravel = IconToken("ic_card_travel", resourceName = "ic_card_travel"),
            colon = IconToken("ic_colon", resourceName = "ic_colon"),
            creditCard = IconToken("ic_credit_card", resourceName = "ic_credit_card"),
            creditCardClock = IconToken("ic_credit_card_clock", resourceName = "ic_credit_card_clock"),
            creditCardGear = IconToken("ic_credit_card_gear", resourceName = "ic_credit_card_gear"),
            creditCardHeart = IconToken("ic_credit_card_heart", resourceName = "ic_credit_card_heart"),
            creditCardOff = IconToken("ic_credit_card_off", resourceName = "ic_credit_card_off"),
            creditScore = IconToken("ic_credit_score", resourceName = "ic_credit_score"),
            dollar = IconToken("ic_dollar", resourceName = "ic_dollar"),
            dollarCircle = IconToken("ic_dollar_circle", resourceName = "ic_dollar_circle"),
            dollarExchange = IconToken("ic_dollar_exchange", resourceName = "ic_dollar_exchange"),
            dollarOff = IconToken("ic_dollar_off", resourceName = "ic_dollar_off"),
            euro = IconToken("ic_euro", resourceName = "ic_euro"),
            franc = IconToken("ic_franc", resourceName = "ic_franc"),
            lira = IconToken("ic_lira", resourceName = "ic_lira"),
            moneyBag = IconToken("ic_money_bag", resourceName = "ic_money_bag"),
            moneyRange = IconToken("ic_money_range", resourceName = "ic_money_range"),
            paymentArrowDown = IconToken("ic_payment_arrow_down", resourceName = "ic_payment_arrow_down"),
            paymentCard = IconToken("ic_payment_card", resourceName = "ic_payment_card"),
            payments = IconToken("ic_payments", resourceName = "ic_payments"),
            pound = IconToken("ic_pound", resourceName = "ic_pound"),
            productionQuantityLimits = IconToken("ic_production_quantity_limits", resourceName = "ic_production_quantity_limits"),
            psychiatry = IconToken("ic_psychiatry", resourceName = "ic_psychiatry"),
            redeem = IconToken("ic_redeem", resourceName = "ic_redeem"),
            ruble = IconToken("ic_ruble", resourceName = "ic_ruble"),
            rupee = IconToken("ic_rupee", resourceName = "ic_rupee"),
            savings = IconToken("ic_savings", resourceName = "ic_savings"),
            sendMoney = IconToken("ic_send_money", resourceName = "ic_send_money"),
            shoppingBag = IconToken("ic_shopping_bag", resourceName = "ic_shopping_bag"),
            shoppingBagSpeed = IconToken("ic_shopping_bag_speed", resourceName = "ic_shopping_bag_speed"),
            shoppingCart = IconToken("ic_shopping_cart", resourceName = "ic_shopping_cart"),
            shoppingCartCheckout = IconToken("ic_shopping_cart_checkout", resourceName = "ic_shopping_cart_checkout"),
            shoppingCartOff = IconToken("ic_shopping_cart_off", resourceName = "ic_shopping_cart_off"),
            sprint = IconToken("ic_sprint", resourceName = "ic_sprint"),
            toll = IconToken("ic_toll", resourceName = "ic_toll"),
            universalCurrency = IconToken("ic_universal_currency", resourceName = "ic_universal_currency"),
            universalCurrencyAlt = IconToken("ic_universal_currency_alt", resourceName = "ic_universal_currency_alt"),
            wallet = IconToken("ic_wallet", resourceName = "ic_wallet"),
            yen = IconToken("ic_yen", resourceName = "ic_yen")

        )

    }
}
