# Markdown for PDF generation


[Markdown][1] is a plain text format for writing structured documents, based on formatting conventions from email and usenet. *Markdown* is a simple way to format text that looks great on any device. It doesn’t do anything fancy like change the font size, color, or type — just the essentials, using keyboard symbols you already know.

There are different implementations of Markdown, we use the [Common Markdown][2] is intended to be as easy-to-read and easy-to-write as is feasible.

Readability, however, is emphasized above all else. A Markdown-formatted documen` should be publishable as-is, as plain text, without looking like it’s been marked up with tags or formatting instructions. While Markdown’s syntax has been influenced by several existing text-to-HTML filters — including Setext, atx, Textile, reStructuredText, Grutatext, and EtText — the single biggest source of inspiration for Markdown’s syntax is the format of plain text email.

To this end, Markdown’s syntax is comprised entirely of punctuation characters, which punctuation characters have been carefully chosen so as to look like what they mean. E.g., asterisks around a word actually look like *emphasis*. Markdown lists look like, well, lists. Even blockquotes look like quoted passages of text, assuming you’ve ever used email.

For details look the [Common Mark Manual](https://commonmark.org/help/). There are many ways to write markdown; a simple offline editor is [Typora][3]. 

[1]: https://en.wikipedia.org/wiki/Markdown "Markdown is a lightweight markup language for creating formatted text using a plain-text editor."
[2]: https://spec.commonmark.org/ "A strongly defined, highly compatible specification of Markdown"
[3]: https://typora.io/ "A truly minimal Mardkown editor"


## Common Mark

A strongly defined, highly compatible specification of Markdown



### Headings
Starting a line with a hash # and a space makes a header.

The more #, the smaller the header.

	# Manual title
	## Chapter
	### Sub Chapter 


!w Remember: Each *markdown* file should be handled as independent manual. Including a file into another it becomes automatically a child chapter. So use the highest heading in each file.



### Paragraphs

A paragraph is consecutive lines of text with one or more blank lines between them.

	lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
	
	invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.

To organize the text thematic 3 -, _, or * can be used. On PDF this will create a page break.

	lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy 
	___
	eirmod tempor


lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy 
___
eirmod tempor



### Line Breaks

For a line break, add either a backslash \ or two blank spaces at the end of the line; this is interpreted as continuation of the text. This is useful if the markdown text should be be wrapped for better reading, but logically the text remains on the same line.

	lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy··
	eirmod tempor
	
lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy  
eirmod tempor


A text with less then two spaces is interpreted as simple line break

	lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy 
	eirmod tempor

lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy 
eirmod tempor



### Blockquotes

To create a blockquote, start a line with greater than > followed by an optional space.

Blockquotes can be nested, and can also contain other formatting.

	> First line
	> Another line
	>
	> > Nested line
	>
	> Last line	

> First line
> Another line
>
> > Nested line
>
> Last line



### Emphasis

To create bold or italic, wrap with asterisks \* or underscores \_. To avoid creating bold or italic, place a backslash in front \\\* or \\\_. For *italics* a single asteriks or underscore is used, for **bold** 2 are used.  Using 3 allows to create a ***bold and italic*** text.

The smart.IO implementation supports an additional syntax for ____underline____ with 4 underscores  and ~~strikethrough~~ with 2 tilde.


	*italic* _italic_
	**bold** __bold__
	***bold & italic text***
	~~strikethrough~~
	____underline____


### Lists

Unordered lists can use either asterisks *, plus +, or hyphens - as list markers.

	- Apples
	- Oranges
	- Pears

- Apples
- Oranges
- Pears

Ordered lists use numbers followed by period . or right paren ).

	1. First
	2. Second
	3. Third

1. First
2. Second
3. Third


### Links

Links can be either inline with the text, or placed at the bottom of the text as references.

Link text is enclosed by square brackets [], and for inline links, the link URL is enclosed by parens ().

	[text](http://a.com)

Links can be realized as footnotes, defining an identifier by square brackets [] and the link URL defined below in the page.

	[][id]
	...
	[id]: http://b.org/ "text"

or as alternative with an inline link

	[text][id]
	...
	[id]: http://b.org/ "text"
	

### Images

Images are almost identical to links, but an image starts with an exclamation point !.

	![](markdown.png)
	![text](markdown.png)

![text](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAeCAQAAADArVVKAAABJWlDQ1BJQ0MgcHJvZmlsZQAAKJGdkD1KxFAUhb/MiH9opVioRQoRLAZsTGUzKgRBIcYRHK0ySQYHkxiSDIM7cCe6mCkEwR24AQVrz4sWFqbxweV8XO49570HLTsJ03JmF9KsKly/27/sX9lzb7TZYIEdtoOwzLued0Lj+XzFMvrSMV7Nc3+e2SguQ+lUlYV5UYG1L3YmVW5Yxeptzz8UP4jtKM0i8ZN4K0ojw2bXT5Nx+ONpbrMUZxfnpq/axOWYUzxsBowZkVDRkWbqHOGwJ3UpCLinJJQmxOpNNFNxIyrl5HIg6ol0m4a89TrPU8pAHiN5mYQ7UnmaPMz/fq99nNWb1to0D4qgbrVVreEQ3h9huQ8rz7B43ZA1//ttDTNOPfPPN34BFKlQexFMbQcAAAACYktHRAD/h4/MvwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB+UDGwkqM5WPHG4AAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAACJUlEQVRIx7WWTUiUQRjHf/PuqxWWepMQiXSRLuYlECIvBuJND5UFtgoGdSkEsQ6CVIcgO5id6lCiYHSoWyhFQpB06VCelDYK6cOi2JJ2i9F9dzo4TO375bbu+8xl5vnPzPPM8zkCiwRD1CMoLSneco0p6COLimhk6RUsso/nXCdb4hfYDHCQJXBQHCEKOorCsbBQrEciYB2wLCImO29lUa1nkoxrZzk79SyDzEMq9S0Oq/7hlKNTz3eTRiKRJKl1KXJXI5I+l1Jzmv/SpW4XCmV59NwYcSbZYbiCQboN5j5TpvllfvoH+6CNKwbt4BJFeiv4mOAsCQAamWBbsU4O0yvGOC1UMU1NqaLITZVMs8CB0oXpX0rroGygwXAqiimIQQIm2U/rP+sUI9zIE7CLvdrIFZqznSYcAN7zfTMBGU7yjDqT9Gf47MmfcQ4BwvixnhcoYIH2Qpy8TIJf+qpR7vsYsYd32MTMuwQxbL5wglRhUfSU8+SAh1xG+eAfOcY3F+8nx0kWGqZwkzss0s9aAP6KXn7n1c/TzBeeB+AwQCdfQ3bMMqgdCzkucu9/Em3D2clNOu8txrQBJ7jqNeXW+0GOYR4ATzhn3hKYB5JZzVny7Ewxo9X54ELWOEWaCzrmQvsBCDP8yl8Qgi+3C4WyPeLC7F0EFnlPtpCIrZTjEKoBpOAR7fxgLoKP12GqeQzNrET2dfxEs4jzZg/9NEbw+X3N7fjyHzpu6IUgF9LcAAAAAElFTkSuQmCC)

We support the non official specification to provide a size and alignment for the image. The optional parameters are defined between brackets {}.

	![text](markdown.png){width=4cm align=center}

![text](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAeCAQAAADArVVKAAABJWlDQ1BJQ0MgcHJvZmlsZQAAKJGdkD1KxFAUhb/MiH9opVioRQoRLAZsTGUzKgRBIcYRHK0ySQYHkxiSDIM7cCe6mCkEwR24AQVrz4sWFqbxweV8XO49570HLTsJ03JmF9KsKly/27/sX9lzb7TZYIEdtoOwzLued0Lj+XzFMvrSMV7Nc3+e2SguQ+lUlYV5UYG1L3YmVW5Yxeptzz8UP4jtKM0i8ZN4K0ojw2bXT5Nx+ONpbrMUZxfnpq/axOWYUzxsBowZkVDRkWbqHOGwJ3UpCLinJJQmxOpNNFNxIyrl5HIg6ol0m4a89TrPU8pAHiN5mYQ7UnmaPMz/fq99nNWb1to0D4qgbrVVreEQ3h9huQ8rz7B43ZA1//ttDTNOPfPPN34BFKlQexFMbQcAAAACYktHRAD/h4/MvwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB+UDGwkqM5WPHG4AAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAACJUlEQVRIx7WWTUiUQRjHf/PuqxWWepMQiXSRLuYlECIvBuJND5UFtgoGdSkEsQ6CVIcgO5id6lCiYHSoWyhFQpB06VCelDYK6cOi2JJ2i9F9dzo4TO375bbu+8xl5vnPzPPM8zkCiwRD1CMoLSneco0p6COLimhk6RUsso/nXCdb4hfYDHCQJXBQHCEKOorCsbBQrEciYB2wLCImO29lUa1nkoxrZzk79SyDzEMq9S0Oq/7hlKNTz3eTRiKRJKl1KXJXI5I+l1Jzmv/SpW4XCmV59NwYcSbZYbiCQboN5j5TpvllfvoH+6CNKwbt4BJFeiv4mOAsCQAamWBbsU4O0yvGOC1UMU1NqaLITZVMs8CB0oXpX0rroGygwXAqiimIQQIm2U/rP+sUI9zIE7CLvdrIFZqznSYcAN7zfTMBGU7yjDqT9Gf47MmfcQ4BwvixnhcoYIH2Qpy8TIJf+qpR7vsYsYd32MTMuwQxbL5wglRhUfSU8+SAh1xG+eAfOcY3F+8nx0kWGqZwkzss0s9aAP6KXn7n1c/TzBeeB+AwQCdfQ3bMMqgdCzkucu9/Em3D2clNOu8txrQBJ7jqNeXW+0GOYR4ATzhn3hKYB5JZzVny7Ewxo9X54ELWOEWaCzrmQvsBCDP8yl8Qgi+3C4WyPeLC7F0EFnlPtpCIrZTjEKoBpOAR7fxgLoKP12GqeQzNrET2dfxEs4jzZg/9NEbw+X3N7fjyHzpu6IUgF9LcAAAAAElFTkSuQmCC){width=4cm align=center}



### Code

To create `inline code`, wrap with backticks `.

	`inline code`

To create a code block, either indent each line by 4 spaces, or place 3 backticks \`\`\` on a line above and below the code block.

	```
	code block
	```




## Extended Syntax

Advanced features that build on the basic Markdown syntax.


### Multi-Files

The smart.IO *Markdown* parser supports organizing the documentation in multiple files. Each file can be included by a simple link with the relative path. The documentation generator will merge all files into a single *markdown* source. Each file should be designed as independent file, the generator takes care about building the correct structure of chapters.

	[Title](local_path/to_file.md)

!w An included file will be handled as sub-chapter of the current chapter.


### Comments

Most Markdown implementatios support comments. 

	[TEXT]: # (Some comment)

[comment]: # (Still another comment)

### Internal Links

Markdown allows to link to internal headers using as link the header name in lower case, prefixed with a hash, and replacing all spaces with a sign.

	[text](#header-of-document)
	
	### Header of Document

The smart.IO framework changes to the local references automatically, that you can directly use the header. Nevertheless you should avoid headers with special characters.

	[text](#Header of Document)
	
	### Header of Document


	
### Tables

To add a table, use three or more hyphens (---) to create each column’s header, and use pipes (|) to separate each column. You can optionally add pipes on either end of the table. We support the extended syntax to align the columns. By default the columns are left aligned, using colons before or after the hypens it is possible to define a left (:---), center (:---:) or right alignment (---:)

	| Auto | Center Alignment | Left Alignment | Right Alignment |
	| ---- | :--------------: | :------------- | --------------: |
	| Auto | Center | Left | Right |


| Auto | Center Alignment | Left Alignment | Right Alignment |
| ---- | :--------------: | :------------- | --------------: |
| Auto | Center | Left | Right |


Sizing of of the single table columns is not supported by markdown. In smart.IO we introduced the definition of the column width, using the number of hyphens (---) in the header separator. The number of hyphens in a single column relative to the sum of all hyphens in all columns define the relative width to the whole table.



### Alerts


Alerts are a smart.IO extension to provide customizable blockquotes. 4 different kind of alerts are supported, that are rendered with a background and highlight color. The alert message have to start with **!** at the beginning of the line with a letter defining the alert message.

	!n Note

!n Note

	!s Success

!s Success

	!w Warning

!w Warning

	!e Error

!e Error


Sometimes multiline alerts are need. Multiline alert starts with double **!!**. The message will be terminated with a single line of **!!**.

	!!w Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
	
	Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
	!!

!!w Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
!!



### Symbols

The smart.IO implementation allows to define placeholders for font symbols. The symbol name must be defined between 2 @.

	@web@

@web@

As font the [Material Icons Font][3] is used.


[3]: https://material.io/resources/icons/ "Material Icons Font"



### Formatted Code

The smart.IO implementation supports the extended syntax with 3 tilde (~), optionally to defining the the code formatter.

	~~~ini
	[datasource.DS1]
	name   = CAFM 
	uri    = http://localhost5000/
	~~~

~~~ini
[datasource.DS1]
name   = CAFM 
uri    = http://localhost5000/
~~~


The *language* attribute allows to define the formatting for a specific language. *ini*, *xml*, *json*, *java* and *cpp* are supported. Support for *rest* is missing. 
