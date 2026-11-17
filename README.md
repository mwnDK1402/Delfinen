[![Stale TODO check](https://github.com/mwnDK1402/Delfinen/actions/workflows/todo-stale.yml/badge.svg)](https://github.com/mwnDK1402/Delfinen/actions/workflows/todo-stale.yml)

## Gruppen

| Fornavn | GitHub                                    |
|---------|-------------------------------------------|
| Mathias | [mwnDK1402](https://github.com/mwnDK1402) |

## Sprog og konventioner

Vi bruger to sprog i projektet, afhængigt af hvem der læser det:

| Hvad                               | Sprog   | Eksempel                        |
|------------------------------------|---------|---------------------------------|
| Kode (klasser, metoder, variabler) | Engelsk | `Competition`, `calculateFee()` |
| UI-tekster og fejlbeskeder         | Dansk   | `"Indtast et helt tal."`        |
| GitHub issues (stories og tasks)   | Dansk   | "Opret et medlem"               |
| Dokumentation i `docs/`            | Dansk   | "Som kasserer vil jeg…"         |
| Commit-beskeder og PR-titler       | Engelsk | `Fix unreachable kill branch`   |

**Domæneord:** Når et dansk domæneord oversættes til en engelsk klasse (fx *stævne* → `Competition`), skal det danske
ord fremgå af Javadoc'en på klassen. Søger man efter *stævne* i koden, finder man altså klassen med det samme.

```java
/**
 * Et svømmestævne (Danish: <i>stævne</i>).
 * Repræsenterer en konkurrence, hvor medlemmer deltager.
 */
public class Competition {
    // ...
}
```

**Hvorfor:** Koden er teknisk og følger engelske standarder for at fungere i IDE'er og værktøjer.
Kommentarer, dokumentation og UI er på dansk, fordi de beskriver domænet for mennesker.