# Compiler

## Grammatik

### Terminale / Tokens

[TokenType.java](./src/main/java/de/nkilders/compiler/TokenType.java)

#### Statisch

Token mit fester Länge und fester Inhalt

| Technischer Name | Beschreibung                     | Beispiel |
| ---------------- | -------------------------------- | -------- |
| `LPAREN`         | Öffnende Klammer                 | `(`      |
| `RPAREN`         | Schließende Klammer              | `)`      |
| `LBRACE`         | Öffnende geschwungene Klammer    | `{`      |
| `RBRACE`         | Schließende geschwungene Klammer | `}`      |
| `LBRACKET`       | Öffnende eckige Klammer          | `[`      |
| `RBRACKET`       | Schließende eckige Klammer       | `]`      |
| `PLUS`           | Plus                             | `+`      |
| `MINUS`          | Minus                            | `-`      |
| `MULTIPLY`       | Mal                              | `*`      |
| `DIVIDE`         | Geteilt                          | `/`      |
| `MODULO`         | Modulo                           | `%`      |
| `EQUALS`         | Vergleich                        | `==`     |
| `ASSIGN`         | Zuweisung                        | `=`      |
| `GT`             | Größer als                       | `>`      |
| `GTE`            | Größer als oder gleich           | `>=`     |
| `LT`             | Kleiner als                      | `<`      |
| `LTE`            | Kleiner als oder gleich          | `<=`     |
| `SEMICOLON`      | Semikolon                        | `;`      |
| `COMMA`          | Komma                            | `,`      |
| `AND`            | Logisches Und                    | `&&`     |
| `OR`             | Logisches Oder                   | `\|\|`   |
| `NOT`            | Logisches Nicht                  | `!`      |

#### Dynamisch

Token mit variabler Länge und variablem Inhalt

| Technischer Name     | Beschreibung           | Beispiel             |
| -------------------- | ---------------------- | -------------------- |
| `IDENTIFIER`         | Bezeichner             | `doStuff`, `hehe123` |
| `STRING`             | String                 | `"abc"`              |
| `LINE_COMMENT`       | Einzeiliger Kommentar  | `// hello world`     |
| `MULTI_LINE_COMMENT` | Mehrzeiliger Kommentar | `/* hello world */`  |
| `WHITESPACE`         | Leerzeichen            | ` `                  |
| `NUMBER`             | Zahl                   | `123`, `3.141`       |

#### Statisch + Bezeichnerartig (Reserved Keywords)

Statische Keywords mit der gleichen Form wie `IDENTIFIER` (s. [Dynamisch](#dynamisch))

[ReservedKeyword.java](./src/main/java/de/nkilders/compiler/ReservedKeyword.java)

| Technischer Name | Beschreibung | Beispiel   |
| ---------------- | ------------ | ---------- |
| `FUNCTION`       |              | `function` |
| `LET`            |              | `let`      |
| `CONST`          |              | `const`    |
| `IF`             |              | `if`       |
| `ELSE`           |              | `else`     |
| `WHILE`          |              | `while`    |

### Regeln

| Nichtterminal | Ableitungen                                                                 |
| ------------- | --------------------------------------------------------------------------- |
| `Stmt`        | `BlockStmt` <br> `DeclareStmt`<br> `Expr`                                   |
| `BlockStmt`   | `LBRACE Stmt* RBRACE`                                                       |
| `DeclareStmt` | `( LET \| CONST ) IDENTIFIER ( ASSIGN Expr )?`                              |
| `Expr`        | `AssignExpr`                                                                |
| `AssignExpr`  | `AddExpr` <br> `VarExpr ASSIGN AssignExpr`                                  |
| `AddExpr`     | `MulExpr ( ( PLUS \| MINUS ) MulExpr )*`                                    |
| `MulExpr`     | `UnaryExpr ( ( MULTIPLY \| DIVIDE ) UnaryExpr )*`                           |
| `UnaryExpr`   | `PrimaryExpr` <br> `( ( PLUS \| MINUS \| NOT ) UnaryExpr )`                 |
| `PrimaryExpr` | `NUMBER`<br>`TRUE`<br> `FALSE`<br> `STRING` <br> `VarExpr` <br> `ParenExpr` |
| `VarExpr`     | `IDENTIFIER`                                                                |
| `ParenExpr`   | `LPAREN Expr RPAREN`                                                        |
