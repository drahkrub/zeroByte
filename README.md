# zeroByte, an error reproducer for Spring-AI 1.1.0

Issue: https://github.com/spring-projects/spring-ai/issues/4974

### `invalid byte sequence for encoding "UTF8": 0x00`

...is the error message I got today - this is a reproducer.

- spring-ai 1.1.0 uses pdfbox 3.0.5
- PagePdfDocumentReader may create a document with a text containing a zero byte
- an exception is thrown if such a text should be stored in a database (at least with PostgreSQL)
- downgrading pdfbox to 3.0.3 (which is used by Spring-AI 1.0.0) fixes the problem
- with pdfbox 3.0.4, 3.0.5 and the current 3.0.6 the error persists
- up to know I wasn't able to reproduce the error in pdfbox alone

The problematic pdf (only one page) is `src/main/resources/page5.pdf`.
