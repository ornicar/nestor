package nestor
package domain

case class Country(code: String, name: String) {

  override def toString = name
}

object Country {

  def apply(code: String): Option[Country] = all get code.toUpperCase

  def tuples = all mapValues (_.name)

  lazy val all = Map(
    "AF" -> Country("AF", "Afghanistan"),
    "AL" -> Country("AL", "Albania"),
    "DZ" -> Country("DZ", "Algeria"),
    "AS" -> Country("AS", "American Samoa"),
    "AD" -> Country("AD", "Andorra"),
    "AO" -> Country("AO", "Angola"),
    "AI" -> Country("AI", "Anguilla"),
    "AQ" -> Country("AQ", "Antarctica"),
    "AG" -> Country("AG", "Antigua and Barbuda"),
    "AR" -> Country("AR", "Argentina"),
    "AM" -> Country("AM", "Armenia"),
    "AW" -> Country("AW", "Aruba"),
    "AU" -> Country("AU", "Australia"),
    "AT" -> Country("AT", "Austria"),
    "AZ" -> Country("AZ", "Azerbaijan"),
    "BS" -> Country("BS", "Bahamas"),
    "BH" -> Country("BH", "Bahrain"),
    "BD" -> Country("BD", "Bangladesh"),
    "BB" -> Country("BB", "Barbados"),
    "BY" -> Country("BY", "Belarus"),
    "BE" -> Country("BE", "Belgium"),
    "BZ" -> Country("BZ", "Belize"),
    "BJ" -> Country("BJ", "Benin"),
    "BM" -> Country("BM", "Bermuda"),
    "BT" -> Country("BT", "Bhutan"),
    "BO" -> Country("BO", "Bolivia"),
    "BA" -> Country("BA", "Bosnia and Herzegovina"),
    "BW" -> Country("BW", "Botswana"),
    "BV" -> Country("BV", "Bouvet Island"),
    "BR" -> Country("BR", "Brazil"),
    "IO" -> Country("IO", "British Indian Ocean Territory"),
    "VG" -> Country("VG", "British Virgin Islands"),
    "BN" -> Country("BN", "Brunei"),
    "BG" -> Country("BG", "Bulgaria"),
    "BF" -> Country("BF", "Burkina Faso"),
    "BI" -> Country("BI", "Burundi"),
    "KH" -> Country("KH", "Cambodia"),
    "CM" -> Country("CM", "Cameroon"),
    "CA" -> Country("CA", "Canada"),
    "CV" -> Country("CV", "Cape Verde"),
    "KY" -> Country("KY", "Cayman Islands"),
    "CF" -> Country("CF", "Central African Republic"),
    "TD" -> Country("TD", "Chad"),
    "CL" -> Country("CL", "Chile"),
    "CN" -> Country("CN", "China"),
    "CX" -> Country("CX", "Christmas Island"),
    "CC" -> Country("CC", "Cocos Islands"),
    "CO" -> Country("CO", "Colombia"),
    "KM" -> Country("KM", "Comoros"),
    "CG" -> Country("CG", "Congo"),
    "CK" -> Country("CK", "Cook Islands"),
    "CR" -> Country("CR", "Costa Rica"),
    "HR" -> Country("HR", "Croatia"),
    "CU" -> Country("CU", "Cuba"),
    "CY" -> Country("CY", "Cyprus"),
    "CZ" -> Country("CZ", "Czech Republic"),
    "CI" -> Country("CI", "Côte d'Ivoire"),
    "DK" -> Country("DK", "Denmark"),
    "DJ" -> Country("DJ", "Djibouti"),
    "DM" -> Country("DM", "Dominica"),
    "DO" -> Country("DO", "Dominican Republic"),
    "EC" -> Country("EC", "Ecuador"),
    "EG" -> Country("EG", "Egypt"),
    "SV" -> Country("SV", "El Salvador"),
    "GQ" -> Country("GQ", "Equatorial Guinea"),
    "ER" -> Country("ER", "Eritrea"),
    "EE" -> Country("EE", "Estonia"),
    "ET" -> Country("ET", "Ethiopia"),
    "FK" -> Country("FK", "Falkland Islands"),
    "FO" -> Country("FO", "Faroe Islands"),
    "FJ" -> Country("FJ", "Fiji"),
    "FI" -> Country("FI", "Finland"),
    "FR" -> Country("FR", "France"),
    "GF" -> Country("GF", "French Guiana"),
    "PF" -> Country("PF", "French Polynesia"),
    "TF" -> Country("TF", "French Southern Territories"),
    "GA" -> Country("GA", "Gabon"),
    "GM" -> Country("GM", "Gambia"),
    "GE" -> Country("GE", "Georgia"),
    "DE" -> Country("DE", "Germany"),
    "GH" -> Country("GH", "Ghana"),
    "GI" -> Country("GI", "Gibraltar"),
    "GR" -> Country("GR", "Greece"),
    "GL" -> Country("GL", "Greenland"),
    "GD" -> Country("GD", "Grenada"),
    "GP" -> Country("GP", "Guadeloupe"),
    "GU" -> Country("GU", "Guam"),
    "GT" -> Country("GT", "Guatemala"),
    "GN" -> Country("GN", "Guinea"),
    "GW" -> Country("GW", "Guinea-Bissau"),
    "GY" -> Country("GY", "Guyana"),
    "HT" -> Country("HT", "Haiti"),
    "HM" -> Country("HM", "Heard Island And McDonald Islands"),
    "HN" -> Country("HN", "Honduras"),
    "HK" -> Country("HK", "Hong Kong"),
    "HU" -> Country("HU", "Hungary"),
    "IS" -> Country("IS", "Iceland"),
    "IN" -> Country("IN", "India"),
    "ID" -> Country("ID", "Indonesia"),
    "IR" -> Country("IR", "Iran"),
    "IQ" -> Country("IQ", "Iraq"),
    "IE" -> Country("IE", "Ireland"),
    "IL" -> Country("IL", "Israel"),
    "IT" -> Country("IT", "Italy"),
    "JM" -> Country("JM", "Jamaica"),
    "JP" -> Country("JP", "Japan"),
    "JO" -> Country("JO", "Jordan"),
    "KZ" -> Country("KZ", "Kazakhstan"),
    "KE" -> Country("KE", "Kenya"),
    "KI" -> Country("KI", "Kiribati"),
    "KW" -> Country("KW", "Kuwait"),
    "KG" -> Country("KG", "Kyrgyzstan"),
    "LA" -> Country("LA", "Laos"),
    "LV" -> Country("LV", "Latvia"),
    "LB" -> Country("LB", "Lebanon"),
    "LS" -> Country("LS", "Lesotho"),
    "LR" -> Country("LR", "Liberia"),
    "LY" -> Country("LY", "Libya"),
    "LI" -> Country("LI", "Liechtenstein"),
    "LT" -> Country("LT", "Lithuania"),
    "LU" -> Country("LU", "Luxembourg"),
    "MO" -> Country("MO", "Macao"),
    "MK" -> Country("MK", "Macedonia"),
    "MG" -> Country("MG", "Madagascar"),
    "MW" -> Country("MW", "Malawi"),
    "MY" -> Country("MY", "Malaysia"),
    "MV" -> Country("MV", "Maldives"),
    "ML" -> Country("ML", "Mali"),
    "MT" -> Country("MT", "Malta"),
    "MH" -> Country("MH", "Marshall Islands"),
    "MQ" -> Country("MQ", "Martinique"),
    "MR" -> Country("MR", "Mauritania"),
    "MU" -> Country("MU", "Mauritius"),
    "YT" -> Country("YT", "Mayotte"),
    "MX" -> Country("MX", "Mexico"),
    "FM" -> Country("FM", "Micronesia"),
    "MD" -> Country("MD", "Moldova"),
    "MC" -> Country("MC", "Monaco"),
    "MN" -> Country("MN", "Mongolia"),
    "ME" -> Country("ME", "Montenegro"),
    "MS" -> Country("MS", "Montserrat"),
    "MA" -> Country("MA", "Morocco"),
    "MZ" -> Country("MZ", "Mozambique"),
    "MM" -> Country("MM", "Myanmar"),
    "NA" -> Country("NA", "Namibia"),
    "NR" -> Country("NR", "Nauru"),
    "NP" -> Country("NP", "Nepal"),
    "NL" -> Country("NL", "Netherlands"),
    "AN" -> Country("AN", "Netherlands Antilles"),
    "NC" -> Country("NC", "New Caledonia"),
    "NZ" -> Country("NZ", "New Zealand"),
    "NI" -> Country("NI", "Nicaragua"),
    "NE" -> Country("NE", "Niger"),
    "NG" -> Country("NG", "Nigeria"),
    "NU" -> Country("NU", "Niue"),
    "NF" -> Country("NF", "Norfolk Island"),
    "KP" -> Country("KP", "North Korea"),
    "MP" -> Country("MP", "Northern Mariana Islands"),
    "NO" -> Country("NO", "Norway"),
    "OM" -> Country("OM", "Oman"),
    "PK" -> Country("PK", "Pakistan"),
    "PW" -> Country("PW", "Palau"),
    "PS" -> Country("PS", "Palestine"),
    "PA" -> Country("PA", "Panama"),
    "PG" -> Country("PG", "Papua New Guinea"),
    "PY" -> Country("PY", "Paraguay"),
    "PE" -> Country("PE", "Peru"),
    "PH" -> Country("PH", "Philippines"),
    "PN" -> Country("PN", "Pitcairn"),
    "PL" -> Country("PL", "Poland"),
    "PT" -> Country("PT", "Portugal"),
    "PR" -> Country("PR", "Puerto Rico"),
    "QA" -> Country("QA", "Qatar"),
    "RE" -> Country("RE", "Reunion"),
    "RO" -> Country("RO", "Romania"),
    "RU" -> Country("RU", "Russia"),
    "RW" -> Country("RW", "Rwanda"),
    "SH" -> Country("SH", "Saint Helena"),
    "KN" -> Country("KN", "Saint Kitts And Nevis"),
    "LC" -> Country("LC", "Saint Lucia"),
    "PM" -> Country("PM", "Saint Pierre And Miquelon"),
    "VC" -> Country("VC", "Saint Vincent And The Grenadines"),
    "WS" -> Country("WS", "Samoa"),
    "SM" -> Country("SM", "San Marino"),
    "ST" -> Country("ST", "Sao Tome And Principe"),
    "SA" -> Country("SA", "Saudi Arabia"),
    "SN" -> Country("SN", "Senegal"),
    "RS" -> Country("RS", "Serbia"),
    "CS" -> Country("CS", "Serbia and Montenegro"),
    "SC" -> Country("SC", "Seychelles"),
    "SL" -> Country("SL", "Sierra Leone"),
    "SG" -> Country("SG", "Singapore"),
    "SK" -> Country("SK", "Slovakia"),
    "SI" -> Country("SI", "Slovenia"),
    "SB" -> Country("SB", "Solomon Islands"),
    "SO" -> Country("SO", "Somalia"),
    "ZA" -> Country("ZA", "South Africa"),
    "GS" -> Country("GS", "South Georgia And The South Sandwich Islands"),
    "KR" -> Country("KR", "South Korea"),
    "ES" -> Country("ES", "Spain"),
    "LK" -> Country("LK", "Sri Lanka"),
    "SD" -> Country("SD", "Sudan"),
    "SR" -> Country("SR", "Suriname"),
    "SJ" -> Country("SJ", "Svalbard And Jan Mayen"),
    "SZ" -> Country("SZ", "Swaziland"),
    "SE" -> Country("SE", "Sweden"),
    "CH" -> Country("CH", "Switzerland"),
    "SY" -> Country("SY", "Syria"),
    "TW" -> Country("TW", "Taiwan"),
    "TJ" -> Country("TJ", "Tajikistan"),
    "TZ" -> Country("TZ", "Tanzania"),
    "TH" -> Country("TH", "Thailand"),
    "CD" -> Country("CD", "The Democratic Republic Of Congo"),
    "TL" -> Country("TL", "Timor-Leste"),
    "TG" -> Country("TG", "Togo"),
    "TK" -> Country("TK", "Tokelau"),
    "TO" -> Country("TO", "Tonga"),
    "TT" -> Country("TT", "Trinidad and Tobago"),
    "TN" -> Country("TN", "Tunisia"),
    "TR" -> Country("TR", "Turkey"),
    "TM" -> Country("TM", "Turkmenistan"),
    "TC" -> Country("TC", "Turks And Caicos Islands"),
    "TV" -> Country("TV", "Tuvalu"),
    "VI" -> Country("VI", "U.S. Virgin Islands"),
    "UG" -> Country("UG", "Uganda"),
    "UA" -> Country("UA", "Ukraine"),
    "AE" -> Country("AE", "United Arab Emirates"),
    "GB" -> Country("GB", "United Kingdom"),
    "US" -> Country("US", "United States"),
    "UM" -> Country("UM", "United States Minor Outlying Islands"),
    "UY" -> Country("UY", "Uruguay"),
    "UZ" -> Country("UZ", "Uzbekistan"),
    "VU" -> Country("VU", "Vanuatu"),
    "VA" -> Country("VA", "Vatican"),
    "VE" -> Country("VE", "Venezuela"),
    "VN" -> Country("VN", "Vietnam"),
    "WF" -> Country("WF", "Wallis And Futuna"),
    "EH" -> Country("EH", "Western Sahara"),
    "YE" -> Country("YE", "Yemen"),
    "ZM" -> Country("ZM", "Zambia"),
    "ZW" -> Country("ZW", "Zimbabwe"),
    "AX" -> Country("AX", "Åland Islands")
  )
}
