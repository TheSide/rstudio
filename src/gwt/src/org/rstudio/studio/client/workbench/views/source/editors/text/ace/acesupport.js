define("mode/r_highlight_rules",function(a,b){var e=a("pilot/oop"),c=a("pilot/lang"),d=a("ace/mode/text_highlight_rules").TextHighlightRules,h=function(){var f=c.arrayToMap("function|while|repeat|for|if|in|else|next|break".split("|")),g=c.arrayToMap("NULL|NA|TRUE|FALSE|T|F|Inf|NaN|NA_integer_|NA_real_|NA_character_|NA_complex_".split("|"));this.$rules={start:[{token:"comment",regex:"#.*$"},{token:"string.regexp",regex:"[/](?:(?:\\[(?:\\\\]|[^\\]])+\\])|(?:\\\\/|[^\\]/]))*[/]\\w*\\s*(?=[).,;]|$)"},
{token:"string",regex:'["](?:(?:\\\\.)|(?:[^"\\\\]))*?["]'},{token:"string",regex:'["].*$',next:"qqstring"},{token:"string",regex:"['](?:(?:\\\\.)|(?:[^'\\\\]))*?[']"},{token:"string",regex:"['].*$",next:"qstring"},{token:"constant.numeric",regex:"0[xX][0-9a-fA-F]+[Li]?\\b"},{token:"constant.numeric",regex:"\\d+L\\b"},{token:"constant.numeric",regex:"\\d+(?:\\.\\d*)?(?:[eE][+\\-]?\\d*)?i?\\b"},{token:"constant.numeric",regex:"\\.\\d+(?:[eE][+\\-]?\\d*)?i?\\b"},{token:"constant.language.boolean",regex:"(?:TRUE|FALSE|T|F)\\b"},
{token:"identifier",regex:"`.*`"},{token:function(i){return f[i]?"keyword":g[i]?"constant.language":i=="..."||i.match(/^\.\.\d+$/)?"variable.language":"identifier"},regex:"[a-zA-Z.][a-zA-Z0-9._]*\\b"},{token:"keyword.operator",regex:"[+\\-*\\/^><!&|~$:=]"},{token:"keyword.operator",regex:"%.*%"},{token:"paren",regex:"[[({]"},{token:"paren",regex:"[\\])}]"},{token:"text",regex:"\\s+"}],qqstring:[{token:"string",regex:'(?:(?:\\\\.)|(?:[^"\\\\]))*?"',next:"start"},{token:"string",regex:".+"}],qstring:[{token:"string",
regex:"(?:(?:\\\\.)|(?:[^'\\\\]))*?'",next:"start"},{token:"string",regex:".+"}]}};e.inherits(h,d);b.RHighlightRules=h});define("mode/r",function(a,b){var e=a("pilot/oop"),c=a("ace/mode/text").Mode,d=a("ace/tokenizer").Tokenizer,h=a("mode/r_highlight_rules").RHighlightRules,f=a("ace/mode/matching_brace_outdent").MatchingBraceOutdent,g=function(){this.$tokenizer=new d((new h).getRules());this.$outdent=new f};e.inherits(g,c);b.Mode=g});define("mode/tex_highlight_rules",function(a,b){var e=a("pilot/oop");a("pilot/lang");var c=a("ace/mode/text_highlight_rules").TextHighlightRules,d=function(){this.$rules={start:[{token:"comment",regex:"%.*$"},{token:"text",regex:"\\\\[$&%#\\{\\}]"},{token:"keyword",regex:"\\\\(?:[a-zA-z0-9]+|[^a-zA-z0-9])"},{token:"string",regex:"``",next:"qqstring"},{token:"string",regex:"`",next:"qstring"},{token:"paren",regex:"[[({]"},{token:"paren",regex:"[\\])}]"},{token:"text",regex:"\\s+"}],qqstring:[{token:"string",
regex:"['][']",next:"start"},{token:"comment",regex:"%.*$"},{token:"string",regex:"\\\\[$&%#\\{\\}]"},{token:"keyword",regex:"\\\\(?:[a-zA-z0-9]+|[^a-zA-z0-9])"},{token:"paren",regex:"[[({]"},{token:"paren",regex:"[\\])}]"},{token:"string",regex:"[^\\\\'[({\\])}%]+"},{token:"string",regex:"."}],qstring:[{token:"string",regex:"['](?!['])",next:"start"},{token:"comment",regex:"%.*$"},{token:"string",regex:"\\\\[$&%#\\{\\}]"},{token:"keyword",regex:"\\\\(?:[a-zA-z0-9]+|[^a-zA-z0-9])"},{token:"paren",
regex:"[[({]"},{token:"paren",regex:"[\\])}]"},{token:"string",regex:"[^\\\\'[({\\])}%]+"},{token:"string",regex:"."}]}};e.inherits(d,c);b.TexHighlightRules=d});define("mode/tex",function(a,b){var e=a("pilot/oop"),c=a("ace/mode/text").Mode,d=a("ace/tokenizer").Tokenizer,h=a("mode/tex_highlight_rules").TexHighlightRules,f=a("ace/mode/matching_brace_outdent").MatchingBraceOutdent,g=function(){this.$tokenizer=new d((new h).getRules());this.$outdent=new f};e.inherits(g,c);b.Mode=g});define("mode/sweave_highlight_rules",function(a,b){var e=a("pilot/oop"),c=a("mode/tex_highlight_rules").TexHighlightRules,d=a("mode/r_highlight_rules").RScriptHighlightRules,h=a("ace/mode/text_highlight_rules").TextHighlightRules,f=function(){this.$rules=(new c).getRules();this.$rules.start.unshift({token:"comment",regex:"^\\<\\<.*\\>\\>=.*$",next:"r-start"});this.addRules((new d).getRules(),"r-");this.$rules["r-start"].unshift({token:"comment",regex:"^@(?:\\s.*)?$",next:"start"})};e.inherits(f,h);
b.SweaveHighlightRules=f});define("mode/sweave",function(a,b){var e=a("pilot/oop"),c=a("ace/mode/text").Mode,d=a("ace/tokenizer").Tokenizer,h=a("mode/sweave_highlight_rules").SweaveHighlightRules,f=a("ace/mode/matching_brace_outdent").MatchingBraceOutdent,g=function(){this.$tokenizer=new d((new h).getRules());this.$outdent=new f};e.inherits(g,c);b.Mode=g});define("theme/default",function(a,b){a("pilot/dom");b.cssClass="ace-tm"});