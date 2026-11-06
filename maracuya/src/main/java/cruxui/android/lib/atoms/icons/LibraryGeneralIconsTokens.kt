package cruxui.android.lib.atoms.icons

import cruxui.android.lib.tokens.base.IconToken

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * LibraryGeneralIconsTokens declara los IconToken de una categoría Library.
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
data class LibraryGeneralIconsTokens (
    /** Token acute; puede sustituirse al configurar la categoría del tema. */
    val acute: IconToken,
    /** Token add; puede sustituirse al configurar la categoría del tema. */
    val add: IconToken,
    /** Token add2; puede sustituirse al configurar la categoría del tema. */
    val add2: IconToken,
    /** Token addAPhoto; puede sustituirse al configurar la categoría del tema. */
    val addAPhoto: IconToken,
    /** Token addAd; puede sustituirse al configurar la categoría del tema. */
    val addAd: IconToken,
    /** Token addAlert; puede sustituirse al configurar la categoría del tema. */
    val addAlert: IconToken,
    /** Token addBox; puede sustituirse al configurar la categoría del tema. */
    val addBox: IconToken,
    /** Token addChatOn; puede sustituirse al configurar la categoría del tema. */
    val addChatOn: IconToken,
    /** Token addLink; puede sustituirse al configurar la categoría del tema. */
    val addLink: IconToken,
    /** Token addNotes; puede sustituirse al configurar la categoría del tema. */
    val addNotes: IconToken,
    /** Token addToPhotos; puede sustituirse al configurar la categoría del tema. */
    val addToPhotos: IconToken,
    /** Token air; puede sustituirse al configurar la categoría del tema. */
    val air: IconToken,
    /** Token alarm; puede sustituirse al configurar la categoría del tema. */
    val alarm: IconToken,
    /** Token alarmAdd; puede sustituirse al configurar la categoría del tema. */
    val alarmAdd: IconToken,
    /** Token alarmOff; puede sustituirse al configurar la categoría del tema. */
    val alarmOff: IconToken,
    /** Token alarmOn; puede sustituirse al configurar la categoría del tema. */
    val alarmOn: IconToken,
    /** Token alarmSmartWake; puede sustituirse al configurar la categoría del tema. */
    val alarmSmartWake: IconToken,
    /** Token androidWifi3Bar; puede sustituirse al configurar la categoría del tema. */
    val androidWifi3Bar: IconToken,
    /** Token approvalDelegation; puede sustituirse al configurar la categoría del tema. */
    val approvalDelegation: IconToken,
    /** Token approvalDelegationOff; puede sustituirse al configurar la categoría del tema. */
    val approvalDelegationOff: IconToken,
    /** Token arOnYou; puede sustituirse al configurar la categoría del tema. */
    val arOnYou: IconToken,
    /** Token arStickers; puede sustituirse al configurar la categoría del tema. */
    val arStickers: IconToken,
    /** Token archive; puede sustituirse al configurar la categoría del tema. */
    val archive: IconToken,
    /** Token article; puede sustituirse al configurar la categoría del tema. */
    val article: IconToken,
    /** Token articlePerson; puede sustituirse al configurar la categoría del tema. */
    val articlePerson: IconToken,
    /** Token articleShortcut; puede sustituirse al configurar la categoría del tema. */
    val articleShortcut: IconToken,
    /** Token asterisk; puede sustituirse al configurar la categoría del tema. */
    val asterisk: IconToken,
    /** Token attachEmail; puede sustituirse al configurar la categoría del tema. */
    val attachEmail: IconToken,
    /** Token attachFile; puede sustituirse al configurar la categoría del tema. */
    val attachFile: IconToken,
    /** Token attachFileOff; puede sustituirse al configurar la categoría del tema. */
    val attachFileOff: IconToken,
    /** Token attachment; puede sustituirse al configurar la categoría del tema. */
    val attachment: IconToken,
    /** Token autoDelete; puede sustituirse al configurar la categoría del tema. */
    val autoDelete: IconToken,
    /** Token autoSchedule; puede sustituirse al configurar la categoría del tema. */
    val autoSchedule: IconToken,
    /** Token backHand; puede sustituirse al configurar la categoría del tema. */
    val backHand: IconToken,
    /** Token backlightHigh; puede sustituirse al configurar la categoría del tema. */
    val backlightHigh: IconToken,
    /** Token ballot; puede sustituirse al configurar la categoría del tema. */
    val ballot: IconToken,
    /** Token barChart; puede sustituirse al configurar la categoría del tema. */
    val barChart: IconToken,
    /** Token barChart4Bars; puede sustituirse al configurar la categoría del tema. */
    val barChart4Bars: IconToken,
    /** Token bigtopUpdates; puede sustituirse al configurar la categoría del tema. */
    val bigtopUpdates: IconToken,
    /** Token bluetooth; puede sustituirse al configurar la categoría del tema. */
    val bluetooth: IconToken,
    /** Token bolt; puede sustituirse al configurar la categoría del tema. */
    val bolt: IconToken,
    /** Token book3; puede sustituirse al configurar la categoría del tema. */
    val book3: IconToken,
    /** Token book4Spark; puede sustituirse al configurar la categoría del tema. */
    val book4Spark: IconToken,
    /** Token bookmark; puede sustituirse al configurar la categoría del tema. */
    val bookmark: IconToken,
    /** Token bookmarkAdd; puede sustituirse al configurar la categoría del tema. */
    val bookmarkAdd: IconToken,
    /** Token bookmarkAdded; puede sustituirse al configurar la categoría del tema. */
    val bookmarkAdded: IconToken,
    /** Token bookmarkHeart; puede sustituirse al configurar la categoría del tema. */
    val bookmarkHeart: IconToken,
    /** Token bookmarkManager; puede sustituirse al configurar la categoría del tema. */
    val bookmarkManager: IconToken,
    /** Token bookmarkStar; puede sustituirse al configurar la categoría del tema. */
    val bookmarkStar: IconToken,
    /** Token borderColor; puede sustituirse al configurar la categoría del tema. */
    val borderColor: IconToken,
    /** Token build; puede sustituirse al configurar la categoría del tema. */
    val build: IconToken,
    /** Token businessCenter; puede sustituirse al configurar la categoría del tema. */
    val businessCenter: IconToken,
    /** Token calendarAddOn; puede sustituirse al configurar la categoría del tema. */
    val calendarAddOn: IconToken,
    /** Token calendarCheck; puede sustituirse al configurar la categoría del tema. */
    val calendarCheck: IconToken,
    /** Token calendarClock; puede sustituirse al configurar la categoría del tema. */
    val calendarClock: IconToken,
    /** Token calendarLock; puede sustituirse al configurar la categoría del tema. */
    val calendarLock: IconToken,
    /** Token calendarMonth; puede sustituirse al configurar la categoría del tema. */
    val calendarMonth: IconToken,
    /** Token calendarToday; puede sustituirse al configurar la categoría del tema. */
    val calendarToday: IconToken,
    /** Token captivePortal; puede sustituirse al configurar la categoría del tema. */
    val captivePortal: IconToken,
    /** Token cardioLoad; puede sustituirse al configurar la categoría del tema. */
    val cardioLoad: IconToken,
    /** Token celebration; puede sustituirse al configurar la categoría del tema. */
    val celebration: IconToken,
    /** Token chat; puede sustituirse al configurar la categoría del tema. */
    val chat: IconToken,
    /** Token chatInfo; puede sustituirse al configurar la categoría del tema. */
    val chatInfo: IconToken,
    /** Token check; puede sustituirse al configurar la categoría del tema. */
    val check: IconToken,
    /** Token checkbook; puede sustituirse al configurar la categoría del tema. */
    val checkbook: IconToken,
    /** Token checklist; puede sustituirse al configurar la categoría del tema. */
    val checklist: IconToken,
    /** Token cheer; puede sustituirse al configurar la categoría del tema. */
    val cheer: IconToken,
    /** Token chromeReaderMode; puede sustituirse al configurar la categoría del tema. */
    val chromeReaderMode: IconToken,
    /** Token chromeRestore; puede sustituirse al configurar la categoría del tema. */
    val chromeRestore: IconToken,
    /** Token clarify; puede sustituirse al configurar la categoría del tema. */
    val clarify: IconToken,
    /** Token close; puede sustituirse al configurar la categoría del tema. */
    val close: IconToken,
    /** Token construction; puede sustituirse al configurar la categoría del tema. */
    val construction: IconToken,
    /** Token contactPage; puede sustituirse al configurar la categoría del tema. */
    val contactPage: IconToken,
    /** Token contentCopy; puede sustituirse al configurar la categoría del tema. */
    val contentCopy: IconToken,
    /** Token contentCut; puede sustituirse al configurar la categoría del tema. */
    val contentCut: IconToken,
    /** Token contentPaste; puede sustituirse al configurar la categoría del tema. */
    val contentPaste: IconToken,
    /** Token contentPasteGo; puede sustituirse al configurar la categoría del tema. */
    val contentPasteGo: IconToken,
    /** Token contentPasteOff; puede sustituirse al configurar la categoría del tema. */
    val contentPasteOff: IconToken,
    /** Token contentPasteSearch; puede sustituirse al configurar la categoría del tema. */
    val contentPasteSearch: IconToken,
    /** Token contract; puede sustituirse al configurar la categoría del tema. */
    val contract: IconToken,
    /** Token contractDelete; puede sustituirse al configurar la categoría del tema. */
    val contractDelete: IconToken,
    /** Token contractEdit; puede sustituirse al configurar la categoría del tema. */
    val contractEdit: IconToken,
    /** Token createNewFolder; puede sustituirse al configurar la categoría del tema. */
    val createNewFolder: IconToken,
    /** Token dehaze; puede sustituirse al configurar la categoría del tema. */
    val dehaze: IconToken,
    /** Token designServices; puede sustituirse al configurar la categoría del tema. */
    val designServices: IconToken,
    /** Token discoverTune; puede sustituirse al configurar la categoría del tema. */
    val discoverTune: IconToken,
    /** Token distance; puede sustituirse al configurar la categoría del tema. */
    val distance: IconToken,
    /** Token docs; puede sustituirse al configurar la categoría del tema. */
    val docs: IconToken,
    /** Token docsAddOn; puede sustituirse al configurar la categoría del tema. */
    val docsAddOn: IconToken,
    /** Token doneAll; puede sustituirse al configurar la categoría del tema. */
    val doneAll: IconToken,
    /** Token dragIndicator; puede sustituirse al configurar la categoría del tema. */
    val dragIndicator: IconToken,
    /** Token draw; puede sustituirse al configurar la categoría del tema. */
    val draw: IconToken,
    /** Token driveFileRenameOutline; puede sustituirse al configurar la categoría del tema. */
    val driveFileRenameOutline: IconToken,
    /** Token earthquake; puede sustituirse al configurar la categoría del tema. */
    val earthquake: IconToken,
    /** Token eda; puede sustituirse al configurar la categoría del tema. */
    val eda: IconToken,
    /** Token editCalendar; puede sustituirse al configurar la categoría del tema. */
    val editCalendar: IconToken,
    /** Token editDocument; puede sustituirse al configurar la categoría del tema. */
    val editDocument: IconToken,
    /** Token energyProgramTimeUsed; puede sustituirse al configurar la categoría del tema. */
    val energyProgramTimeUsed: IconToken,
    /** Token equitSquare; puede sustituirse al configurar la categoría del tema. */
    val equitSquare: IconToken,
    /** Token factCheck; puede sustituirse al configurar la categoría del tema. */
    val factCheck: IconToken,
    /** Token featureSearch; puede sustituirse al configurar la categoría del tema. */
    val featureSearch: IconToken,
    /** Token fileCopy; puede sustituirse al configurar la categoría del tema. */
    val fileCopy: IconToken,
    /** Token fileCopyOff; puede sustituirse al configurar la categoría del tema. */
    val fileCopyOff: IconToken,
    /** Token fileDownloadDone; puede sustituirse al configurar la categoría del tema. */
    val fileDownloadDone: IconToken,
    /** Token filterAlt; puede sustituirse al configurar la categoría del tema. */
    val filterAlt: IconToken,
    /** Token filterNone; puede sustituirse al configurar la categoría del tema. */
    val filterNone: IconToken,
    /** Token fingerprint; puede sustituirse al configurar la categoría del tema. */
    val fingerprint: IconToken,
    /** Token flashOn; puede sustituirse al configurar la categoría del tema. */
    val flashOn: IconToken,
    /** Token folder; puede sustituirse al configurar la categoría del tema. */
    val folder: IconToken,
    /** Token folderCheck2; puede sustituirse al configurar la categoría del tema. */
    val folderCheck2: IconToken,
    /** Token folderMatch; puede sustituirse al configurar la categoría del tema. */
    val folderMatch: IconToken,
    /** Token folderOff; puede sustituirse al configurar la categoría del tema. */
    val folderOff: IconToken,
    /** Token folderShared; puede sustituirse al configurar la categoría del tema. */
    val folderShared: IconToken,
    /** Token folderSupervised; puede sustituirse al configurar la categoría del tema. */
    val folderSupervised: IconToken,
    /** Token footprint; puede sustituirse al configurar la categoría del tema. */
    val footprint: IconToken,
    /** Token formatColorReset; puede sustituirse al configurar la categoría del tema. */
    val formatColorReset: IconToken,
    /** Token formatInkHighlighter; puede sustituirse al configurar la categoría del tema. */
    val formatInkHighlighter: IconToken,
    /** Token glucose; puede sustituirse al configurar la categoría del tema. */
    val glucose: IconToken,
    /** Token grade; puede sustituirse al configurar la categoría del tema. */
    val grade: IconToken,
    /** Token handBones; puede sustituirse al configurar la categoría del tema. */
    val handBones: IconToken,
    /** Token handGesture; puede sustituirse al configurar la categoría del tema. */
    val handGesture: IconToken,
    /** Token handGestureOff; puede sustituirse al configurar la categoría del tema. */
    val handGestureOff: IconToken,
    /** Token handshake; puede sustituirse al configurar la categoría del tema. */
    val handshake: IconToken,
    /** Token libraryAddCheck; puede sustituirse al configurar la categoría del tema. */
    val libraryAddCheck: IconToken,
    /** Token liftToTalk; puede sustituirse al configurar la categoría del tema. */
    val liftToTalk: IconToken,
    /** Token link; puede sustituirse al configurar la categoría del tema. */
    val link: IconToken,
    /** Token linkOff; puede sustituirse al configurar la categoría del tema. */
    val linkOff: IconToken,
    /** Token lock; puede sustituirse al configurar la categoría del tema. */
    val lock: IconToken,
    /** Token lookOpen; puede sustituirse al configurar la categoría del tema. */
    val lookOpen: IconToken,
    /** Token lookOpenRight; puede sustituirse al configurar la categoría del tema. */
    val lookOpenRight: IconToken,
    /** Token magicButton; puede sustituirse al configurar la categoría del tema. */
    val magicButton: IconToken,
    /** Token manageSearch; puede sustituirse al configurar la categoría del tema. */
    val manageSearch: IconToken,
    /** Token massage; puede sustituirse al configurar la categoría del tema. */
    val massage: IconToken,
    /** Token medicalServices; puede sustituirse al configurar la categoría del tema. */
    val medicalServices: IconToken,
    /** Token minusBar; puede sustituirse al configurar la categoría del tema. */
    val minusBar: IconToken,
    /** Token modeHeat; puede sustituirse al configurar la categoría del tema. */
    val modeHeat: IconToken,
    /** Token modeHeatOff; puede sustituirse al configurar la categoría del tema. */
    val modeHeatOff: IconToken,
    /** Token moreTime; puede sustituirse al configurar la categoría del tema. */
    val moreTime: IconToken,
    /** Token networkCheck; puede sustituirse al configurar la categoría del tema. */
    val networkCheck: IconToken,
    /** Token newWindow; puede sustituirse al configurar la categoría del tema. */
    val newWindow: IconToken,
    /** Token news; puede sustituirse al configurar la categoría del tema. */
    val news: IconToken,
    /** Token newsmode; puede sustituirse al configurar la categoría del tema. */
    val newsmode: IconToken,
    /** Token notificationAdd; puede sustituirse al configurar la categoría del tema. */
    val notificationAdd: IconToken,
    /** Token notificationImportant; puede sustituirse al configurar la categoría del tema. */
    val notificationImportant: IconToken,
    /** Token notificationSettings; puede sustituirse al configurar la categoría del tema. */
    val notificationSettings: IconToken,
    /** Token notificationSound; puede sustituirse al configurar la categoría del tema. */
    val notificationSound: IconToken,
    /** Token notifications; puede sustituirse al configurar la categoría del tema. */
    val notifications: IconToken,
    /** Token notificationsActive; puede sustituirse al configurar la categoría del tema. */
    val notificationsActive: IconToken,
    /** Token notificationsUnread; puede sustituirse al configurar la categoría del tema. */
    val notificationsUnread: IconToken,
    /** Token pendingActions; puede sustituirse al configurar la categoría del tema. */
    val pendingActions: IconToken,
    /** Token permContactCalendar; puede sustituirse al configurar la categoría del tema. */
    val permContactCalendar: IconToken,
    /** Token photoCamera; puede sustituirse al configurar la categoría del tema. */
    val photoCamera: IconToken,
    /** Token soap; puede sustituirse al configurar la categoría del tema. */
    val soap: IconToken,
    /** Token store; puede sustituirse al configurar la categoría del tema. */
    val store: IconToken,
    /** Token swipe; puede sustituirse al configurar la categoría del tema. */
    val swipe: IconToken,
    /** Token swipeDown; puede sustituirse al configurar la categoría del tema. */
    val swipeDown: IconToken,
    /** Token swipeLeft; puede sustituirse al configurar la categoría del tema. */
    val swipeLeft: IconToken,
    /** Token swipeRight; puede sustituirse al configurar la categoría del tema. */
    val swipeRight: IconToken,
    /** Token swipeUp; puede sustituirse al configurar la categoría del tema. */
    val swipeUp: IconToken,
    /** Token swipeVertical; puede sustituirse al configurar la categoría del tema. */
    val swipeVertical: IconToken,
    /** Token tempPreferencesCustom; puede sustituirse al configurar la categoría del tema. */
    val tempPreferencesCustom: IconToken,
    /** Token thumbDownOff; puede sustituirse al configurar la categoría del tema. */
    val thumbDownOff: IconToken,
    /** Token thumbUp; puede sustituirse al configurar la categoría del tema. */
    val thumbUp: IconToken,
    /** Token thumbsUpDouble; puede sustituirse al configurar la categoría del tema. */
    val thumbsUpDouble: IconToken,
    /** Token thumbsUpDown; puede sustituirse al configurar la categoría del tema. */
    val thumbsUpDown: IconToken,
    /** Token touchApp; puede sustituirse al configurar la categoría del tema. */
    val touchApp: IconToken,
    /** Token touchDouble; puede sustituirse al configurar la categoría del tema. */
    val touchDouble: IconToken,
    /** Token touchLong; puede sustituirse al configurar la categoría del tema. */
    val touchLong: IconToken,
    /** Token travelExplore; puede sustituirse al configurar la categoría del tema. */
    val travelExplore: IconToken,
    /** Token visibility; puede sustituirse al configurar la categoría del tema. */
    val visibility: IconToken,
    /** Token visibilityOff; puede sustituirse al configurar la categoría del tema. */
    val visibilityOff: IconToken,
    /** Token wash; puede sustituirse al configurar la categoría del tema. */
    val wash: IconToken,
    /** Token waterDrop; puede sustituirse al configurar la categoría del tema. */
    val waterDrop: IconToken,
){
    companion object{
        /** Valores base de la categoría; fuente única de sus assets. */
        val general = LibraryGeneralIconsTokens(


            acute = IconToken("ic_acute", resourceName = "ic_acute"),
            add = IconToken("ic_add", resourceName = "ic_add"),
            add2 = IconToken("ic_add_2", resourceName = "ic_add_2"),
            addAPhoto = IconToken("ic_add_a_photo", resourceName = "ic_add_a_photo"),
            addAd = IconToken("ic_add_ad", resourceName = "ic_add_ad"),
            addAlert = IconToken("ic_add_alert", resourceName = "ic_add_alert"),
            addBox = IconToken("ic_add_box", resourceName = "ic_add_box"),
            addChatOn = IconToken("ic_add_chat_on", resourceName = "ic_add_chat_on"),
            addLink = IconToken("ic_add_link", resourceName = "ic_add_link"),
            addNotes = IconToken("ic_add_notes", resourceName = "ic_add_notes"),
            addToPhotos = IconToken("ic_add_to_photos", resourceName = "ic_add_to_photos"),
            air = IconToken("ic_air", resourceName = "ic_air"),
            alarm = IconToken("ic_alarm", resourceName = "ic_alarm"),
            alarmAdd = IconToken("ic_alarm_add", resourceName = "ic_alarm_add"),
            alarmOff = IconToken("ic_alarm_off", resourceName = "ic_alarm_off"),
            alarmOn = IconToken("ic_alarm_on", resourceName = "ic_alarm_on"),
            alarmSmartWake = IconToken("ic_alarm_smart_wake", resourceName = "ic_alarm_smart_wake"),
            androidWifi3Bar = IconToken("ic_android_wifi_3_bar", resourceName = "ic_android_wifi_3_bar"),
            approvalDelegation = IconToken("ic_approval_delegation", resourceName = "ic_approval_delegation"),
            approvalDelegationOff = IconToken("ic_approval_delegation_off", resourceName = "ic_approval_delegation_off"),
            arOnYou = IconToken("ic_ar_on_you", resourceName = "ic_ar_on_you"),
            arStickers = IconToken("ic_ar_stickers", resourceName = "ic_ar_stickers"),
            archive = IconToken("ic_archive", resourceName = "ic_archive"),
            article = IconToken("ic_article", resourceName = "ic_article"),
            articlePerson = IconToken("ic_article_person", resourceName = "ic_article_person"),
            articleShortcut = IconToken("ic_article_shortcut", resourceName = "ic_article_shortcut"),
            asterisk = IconToken("ic_asterisk", resourceName = "ic_asterisk"),
            attachEmail = IconToken("ic_attach_email", resourceName = "ic_attach_email"),
            attachFile = IconToken("ic_attach_file", resourceName = "ic_attach_file"),
            attachFileOff = IconToken("ic_attach_file_off", resourceName = "ic_attach_file_off"),
            attachment = IconToken("ic_attachment", resourceName = "ic_attachment"),
            autoDelete = IconToken("ic_auto_delete", resourceName = "ic_auto_delete"),
            autoSchedule = IconToken("ic_auto_schedule", resourceName = "ic_auto_schedule"),
            backHand = IconToken("ic_back_hand", resourceName = "ic_back_hand"),
            backlightHigh = IconToken("ic_backlight_high", resourceName = "ic_backlight_high"),
            ballot = IconToken("ic_ballot", resourceName = "ic_ballot"),
            barChart = IconToken("ic_bar_chart", resourceName = "ic_bar_chart"),
            barChart4Bars = IconToken("ic_bar_chart_4_bars", resourceName = "ic_bar_chart_4_bars"),
            bigtopUpdates = IconToken("ic_bigtop_updates", resourceName = "ic_bigtop_updates"),
            bluetooth = IconToken("ic_bluetooth", resourceName = "ic_bluetooth"),
            bolt = IconToken("ic_bolt", resourceName = "ic_bolt"),
            book3 = IconToken("ic_book_3", resourceName = "ic_book_3"),
            book4Spark = IconToken("ic_book_4_spark", resourceName = "ic_book_4_spark"),
            bookmark = IconToken("ic_bookmark", resourceName = "ic_bookmark"),
            bookmarkAdd = IconToken("ic_bookmark_add", resourceName = "ic_bookmark_add"),
            bookmarkAdded = IconToken("ic_bookmark_added", resourceName = "ic_bookmark_added"),
            bookmarkHeart = IconToken("ic_bookmark_heart", resourceName = "ic_bookmark_heart"),
            bookmarkManager = IconToken("ic_bookmark_manager", resourceName = "ic_bookmark_manager"),
            bookmarkStar = IconToken("ic_bookmark_star", resourceName = "ic_bookmark_star"),
            borderColor = IconToken("ic_border_color", resourceName = "ic_border_color"),
            build = IconToken("ic_build", resourceName = "ic_build"),
            businessCenter = IconToken("ic_business_center", resourceName = "ic_business_center"),
            calendarAddOn = IconToken("ic_calendar_add_on", resourceName = "ic_calendar_add_on"),
            calendarCheck = IconToken("ic_calendar_check", resourceName = "ic_calendar_check"),
            calendarClock = IconToken("ic_calendar_clock", resourceName = "ic_calendar_clock"),
            calendarLock = IconToken("ic_calendar_lock", resourceName = "ic_calendar_lock"),
            calendarMonth = IconToken("ic_calendar_month", resourceName = "ic_calendar_month"),
            calendarToday = IconToken("ic_calendar_today", resourceName = "ic_calendar_today"),
            captivePortal = IconToken("ic_captive_portal", resourceName = "ic_captive_portal"),
            cardioLoad = IconToken("ic_cardio_load", resourceName = "ic_cardio_load"),
            celebration = IconToken("ic_celebration", resourceName = "ic_celebration"),
            chat = IconToken("ic_chat", resourceName = "ic_chat"),
            chatInfo = IconToken("ic_chat_info", resourceName = "ic_chat_info"),
            check = IconToken("ic_check", resourceName = "ic_check"),
            checkbook = IconToken("ic_checkbook", resourceName = "ic_checkbook"),
            checklist = IconToken("ic_checklist", resourceName = "ic_checklist"),
            cheer = IconToken("ic_cheer", resourceName = "ic_cheer"),
            chromeReaderMode = IconToken("ic_chrome_reader_mode", resourceName = "ic_chrome_reader_mode"),
            chromeRestore = IconToken("ic_chrome_restore", resourceName = "ic_chrome_restore"),
            clarify = IconToken("ic_clarify", resourceName = "ic_clarify"),
            close = IconToken("ic_close", resourceName = "ic_close"),
            construction = IconToken("ic_construction", resourceName = "ic_construction"),
            contactPage = IconToken("ic_contact_page", resourceName = "ic_contact_page"),
            contentCopy = IconToken("ic_content_copy", resourceName = "ic_content_copy"),
            contentCut = IconToken("ic_content_cut", resourceName = "ic_content_cut"),
            contentPaste = IconToken("ic_content_paste", resourceName = "ic_content_paste"),
            contentPasteGo = IconToken("ic_content_paste_go", resourceName = "ic_content_paste_go"),
            contentPasteOff = IconToken("ic_content_paste_off", resourceName = "ic_content_paste_off"),
            contentPasteSearch = IconToken("ic_content_paste_search", resourceName = "ic_content_paste_search"),
            contract = IconToken("ic_contract", resourceName = "ic_contract"),
            contractDelete = IconToken("ic_contract_delete", resourceName = "ic_contract_delete"),
            contractEdit = IconToken("ic_contract_edit", resourceName = "ic_contract_edit"),
            createNewFolder = IconToken("ic_create_new_folder", resourceName = "ic_create_new_folder"),
            dehaze = IconToken("ic_dehaze", resourceName = "ic_dehaze"),
            designServices = IconToken("ic_design_services", resourceName = "ic_design_services"),
            discoverTune = IconToken("ic_discover_tune", resourceName = "ic_discover_tune"),
            distance = IconToken("ic_distance", resourceName = "ic_distance"),
            docs = IconToken("ic_docs", resourceName = "ic_docs"),
            docsAddOn = IconToken("ic_docs_add_on", resourceName = "ic_docs_add_on"),
            doneAll = IconToken("ic_done_all", resourceName = "ic_done_all"),
            dragIndicator = IconToken("ic_drag_indicator", resourceName = "ic_drag_indicator"),
            draw = IconToken("ic_draw", resourceName = "ic_draw"),
            driveFileRenameOutline = IconToken("ic_drive_file_rename_outline", resourceName = "ic_drive_file_rename_outline"),
            earthquake = IconToken("ic_earthquake", resourceName = "ic_earthquake"),
            eda = IconToken("ic_eda", resourceName = "ic_eda"),
            editCalendar = IconToken("ic_edit_calendar", resourceName = "ic_edit_calendar"),
            editDocument = IconToken("ic_edit_document", resourceName = "ic_edit_document"),
            energyProgramTimeUsed = IconToken("ic_energy_program_time_used", resourceName = "ic_energy_program_time_used"),
            equitSquare = IconToken("ic_equit_square", resourceName = "ic_equit_square"),
            factCheck = IconToken("ic_fact_check", resourceName = "ic_fact_check"),
            featureSearch = IconToken("ic_feature_search", resourceName = "ic_feature_search"),
            fileCopy = IconToken("ic_file_copy", resourceName = "ic_file_copy"),
            fileCopyOff = IconToken("ic_file_copy_off", resourceName = "ic_file_copy_off"),
            fileDownloadDone = IconToken("ic_file_download_done", resourceName = "ic_file_download_done"),
            filterAlt = IconToken("ic_filter_alt", resourceName = "ic_filter_alt"),
            filterNone = IconToken("ic_filter_none", resourceName = "ic_filter_none"),
            fingerprint = IconToken("ic_fingerprint", resourceName = "ic_fingerprint"),
            flashOn = IconToken("ic_flash_on", resourceName = "ic_flash_on"),
            folder = IconToken("ic_folder", resourceName = "ic_folder"),
            folderCheck2 = IconToken("ic_folder_check_2", resourceName = "ic_folder_check_2"),
            folderMatch = IconToken("ic_folder_match", resourceName = "ic_folder_match"),
            folderOff = IconToken("ic_folder_off", resourceName = "ic_folder_off"),
            folderShared = IconToken("ic_folder_shared", resourceName = "ic_folder_shared"),
            folderSupervised = IconToken("ic_folder_supervised", resourceName = "ic_folder_supervised"),
            footprint = IconToken("ic_footprint", resourceName = "ic_footprint"),
            formatColorReset = IconToken("ic_format_color_reset", resourceName = "ic_format_color_reset"),
            formatInkHighlighter = IconToken("ic_format_ink_highlighter", resourceName = "ic_format_ink_highlighter"),
            glucose = IconToken("ic_glucose", resourceName = "ic_glucose"),
            grade = IconToken("ic_grade", resourceName = "ic_grade"),
            handBones = IconToken("ic_hand_bones", resourceName = "ic_hand_bones"),
            handGesture = IconToken("ic_hand_gesture", resourceName = "ic_hand_gesture"),
            handGestureOff = IconToken("ic_hand_gesture_off", resourceName = "ic_hand_gesture_off"),
            handshake = IconToken("ic_handshake", resourceName = "ic_handshake"),
            libraryAddCheck = IconToken("ic_library_add_check", resourceName = "ic_library_add_check"),
            liftToTalk = IconToken("ic_lift_to_talk", resourceName = "ic_lift_to_talk"),
            link = IconToken("ic_link", resourceName = "ic_link"),
            linkOff = IconToken("ic_link_off", resourceName = "ic_link_off"),
            lock = IconToken("ic_lock", resourceName = "ic_lock"),
            lookOpen = IconToken("ic_look_open", resourceName = "ic_look_open"),
            lookOpenRight = IconToken("ic_look_open_right", resourceName = "ic_look_open_right"),
            magicButton = IconToken("ic_magic_button", resourceName = "ic_magic_button"),
            manageSearch = IconToken("ic_manage_search", resourceName = "ic_manage_search"),
            massage = IconToken("ic_massage", resourceName = "ic_massage"),
            medicalServices = IconToken("ic_medical_services", resourceName = "ic_medical_services"),
            minusBar = IconToken("ic_minus_bar", resourceName = "ic_minus_bar"),
            modeHeat = IconToken("ic_mode_heat", resourceName = "ic_mode_heat"),
            modeHeatOff = IconToken("ic_mode_heat_off", resourceName = "ic_mode_heat_off"),
            moreTime = IconToken("ic_more_time", resourceName = "ic_more_time"),
            networkCheck = IconToken("ic_network_check", resourceName = "ic_network_check"),
            newWindow = IconToken("ic_new_window", resourceName = "ic_new_window"),
            news = IconToken("ic_news", resourceName = "ic_news"),
            newsmode = IconToken("ic_newsmode", resourceName = "ic_newsmode"),
            notificationAdd = IconToken("ic_notification_add", resourceName = "ic_notification_add"),
            notificationImportant = IconToken("ic_notification_important", resourceName = "ic_notification_important"),
            notificationSettings = IconToken("ic_notification_settings", resourceName = "ic_notification_settings"),
            notificationSound = IconToken("ic_notification_sound", resourceName = "ic_notification_sound"),
            notifications = IconToken("ic_notifications", resourceName = "ic_notifications"),
            notificationsActive = IconToken("ic_notifications_active", resourceName = "ic_notifications_active"),
            notificationsUnread = IconToken("ic_notifications_unread", resourceName = "ic_notifications_unread"),
            pendingActions = IconToken("ic_pending_actions", resourceName = "ic_pending_actions"),
            permContactCalendar = IconToken("ic_perm_contact_calendar", resourceName = "ic_perm_contact_calendar"),
            photoCamera = IconToken("ic_photo_camera", resourceName = "ic_photo_camera"),
            soap = IconToken("ic_soap", resourceName = "ic_soap"),
            store = IconToken("ic_store", resourceName = "ic_store"),
            swipe = IconToken("ic_swipe", resourceName = "ic_swipe"),
            swipeDown = IconToken("ic_swipe_down", resourceName = "ic_swipe_down"),
            swipeLeft = IconToken("ic_swipe_left", resourceName = "ic_swipe_left"),
            swipeRight = IconToken("ic_swipe_right", resourceName = "ic_swipe_right"),
            swipeUp = IconToken("ic_swipe_up", resourceName = "ic_swipe_up"),
            swipeVertical = IconToken("ic_swipe_vertical", resourceName = "ic_swipe_vertical"),
            tempPreferencesCustom = IconToken("ic_temp_preferences_custom", resourceName = "ic_temp_preferences_custom"),
            thumbDownOff = IconToken("ic_thumb_down_off", resourceName = "ic_thumb_down_off"),
            thumbUp = IconToken("ic_thumb_up", resourceName = "ic_thumb_up"),
            thumbsUpDouble = IconToken("ic_thumbs_up_double", resourceName = "ic_thumbs_up_double"),
            thumbsUpDown = IconToken("ic_thumbs_up_down", resourceName = "ic_thumbs_up_down"),
            touchApp = IconToken("ic_touch_app", resourceName = "ic_touch_app"),
            touchDouble = IconToken("ic_touch_double", resourceName = "ic_touch_double"),
            touchLong = IconToken("ic_touch_long", resourceName = "ic_touch_long"),
            travelExplore = IconToken("ic_travel_explore", resourceName = "ic_travel_explore"),
            visibility = IconToken("ic_visibility", resourceName = "ic_visibility"),
            visibilityOff = IconToken("ic_visibility_off", resourceName = "ic_visibility_off"),
            wash = IconToken("ic_wash", resourceName = "ic_wash"),
            waterDrop = IconToken("ic_water_drop", resourceName = "ic_water_drop"),
        )
    }
}
