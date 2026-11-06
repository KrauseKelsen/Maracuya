package schema

import cruxui.android.lib.semantics.CorporateTypography
import org.junit.Assert.*
import org.junit.Test

/** Verifica ampliación de catálogo y compatibilidad sin mantener otra lista de tipografías admitidas. */
class TypographySchemaTest {
    @Test fun `el esquema y la API efectiva incluyen exactamente las propiedades de Library`() {
        assertEquals(TypographySchema.names().toSet(), CorporateTypography().asMap().keys)
    }

    @Test fun `agregar un nombre produce propiedad consulta y rol sin editar otras tablas`() {
        val previous = TypographySchema.names()
        val extended = TypographySchema.orderedNames(previous.reversed() + "campaignTitle")
        assertEquals(previous, extended.filter { it != "campaignTitle" })
        val corporate = LibraryTypographySource.render(extended)
        assertTrue(corporate.contains("val campaignTitle: TypographyToken = LibraryTypographyTokens.campaignTitle"))
        assertTrue(corporate.contains("\"campaignTitle\" -> campaignTitle"))
        assertTrue(corporate.contains("\"campaignTitle\" to campaignTitle"))
        assertTrue(TypographyRolesSource.render(extended).contains("CampaignTitle(\"campaignTitle\")"))
    }

    @Test fun `los alias historicos no limitan el catalogo generado`() {
        val roles = TypographyRolesSource.render(TypographySchema.names())
        assertTrue(roles.contains("Body(\"body1\")"))
        assertTrue(roles.contains("Body1(\"body1\")"))
        assertTrue(roles.contains("Headline24Medium(\"headline24Medium\")"))
        assertTrue(roles.contains("LegalBold(\"legalBold\")"))
    }
}
