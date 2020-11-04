package br.com.rsms.calculator.model.enums

enum class OperationsEnum(val operation: String) {

    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    SQUARE_ROOT("sqrt("),
    FACTORIAL("!"),
    MODULUS("#"),
    EXPONENTIATION("^"),
    EXPONENTIATION_2("^2"),
    LOG("log2("),
    SIN("sin("),
    COS("cos("),
    TG("tg("),
    ASIN("asin("),
    ACOS("acos("),
    ATG("atg("),
    LN("ln("),
    PARENTHESIS_OPEN("("),
    PARENTHESIS_CLOSE(")"),
    EQUALS("=")
    
}