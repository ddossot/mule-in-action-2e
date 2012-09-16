<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:param name="transactionId" />

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="payment">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
            <xsl:attribute name="tid">
                <xsl:value-of select="$transactionId" />
            </xsl:attribute>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>