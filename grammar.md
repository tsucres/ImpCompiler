# Imp grammar

<table>
<tbody>
<tr class="">
<td align="right">(1)</td>
<td align="left">&lt;Func&gt;</td>
<td align="left"><span class="math inline">→</span> <code>func</code> [VarName] <code>with</code> &lt;FuncSuffix&gt;</td>
</tr>
<tr class="">
<td align="right">(2)</td>
<td align="left">&lt;FuncSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> <code>in</code> &lt;Code&gt; &lt;ReturnStmt&gt;</td>
</tr>
<tr class="">
<td align="right">(3)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> [VarName] &lt;FuncSuffix&gt;</td>
</tr>
<tr class="">
<td align="right">(4)</td>
<td align="left">&lt;ReturnStmt&gt;</td>
<td align="left"><span class="math inline">→</span> <code>return</code> &lt;ExprArith&gt; <code>endfunc</code></td>
</tr>
<tr class="odd">
<td align="right">(5)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>endfunc</code></td>
</tr>
<tr class="even">
<td align="right">(6)</td>
<td align="left">&lt;Program&gt;</td>
<td align="left"><span class="math inline">→</span> <code>begin</code> &lt;Code&gt; <code>end</code></td>
</tr>
<tr class="odd">
<td align="right">(7)</td>
<td align="left">&lt;Code&gt;</td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="even">
<td align="right">(8)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;InstList&gt;</td>
</tr>
<tr class="odd">
<td align="right">(9)</td>
<td align="left">&lt;InstList&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;Instruction&gt; &lt;InstListSuffix&gt;</td>
</tr>
<tr class="even">
<td align="right">(10)</td>
<td align="left">&lt;InstListSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="odd">
<td align="right">(11)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>;</code> &lt;InstList&gt;</td>
</tr>
<tr class="even">
<td align="right">(12)</td>
<td align="left">&lt;Instruction&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;Assign&gt;</td>
</tr>
<tr class="odd">
<td align="right">(13)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;If&gt;</td>
</tr>
<tr class="even">
<td align="right">(14)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;While&gt;</td>
</tr>
<tr class="odd">
<td align="right">(15)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;For&gt;</td>
</tr>
<tr class="even">
<td align="right">(16)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;Print&gt;</td>
</tr>
<tr class="odd">
<td align="right">(17)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;Read&gt;</td>
</tr>
<tr class="even">
<td align="right">(18)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;FuncCall&gt;</td>
</tr>
<tr class="odd">
<td align="right">(19)</td>
<td align="left">&lt;FuncCall&gt;</td>
<td align="left"><span class="math inline">→</span> <code>call</code> [VarName] <code>(</code> &lt;FuncCallSuffix&gt;</td>
</tr>
<tr class="even">
<td align="right">(20)</td>
<td align="left">&lt;FuncCallSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;ExprArithm&gt; &lt;FuncCallSuffix&gt;</td>
</tr>
<tr class="odd">
<td align="right">(21)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>)</code></td>
</tr>
<tr class="even">
<td align="right">(22)</td>
<td align="left">&lt;Assign&gt;</td>
<td align="left"><span class="math inline">→</span> [VarName] <code>:=</code> &lt;AssignSuffix&gt;</td>
</tr>
<tr class="odd">
<td align="right">(23)</td>
<td align="left">&lt;AssignSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> ExprArith</td>
</tr>
<tr class="even">
<td align="right">(24)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> [String]</td>
</tr>
<tr class="odd">
<td align="right">(25)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;FuncCall&gt;</td>
</tr>
<tr class="even">
<td align="right">(26)</td>
<td align="left">&lt;ExprArith&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;EA1&gt;&lt;EA2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(27)</td>
<td align="left">&lt;EA1&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;Prod&gt;</td>
</tr>
<tr class="even">
<td align="right">(28)</td>
<td align="left">&lt;EA2&gt;</td>
<td align="left"><span class="math inline">→</span> <code>+</code> &lt;Prod&gt;&lt;EA2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(29)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>-</code> &lt;Prod&gt;&lt;EA2&gt;</td>
</tr>
<tr class="even">
<td align="right">(30)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="odd">
<td align="right">(31)</td>
<td align="left">&lt;Prod&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;P1&gt;&lt;P2&gt;</td>
</tr>
<tr class="even">
<td align="right">(32)</td>
<td align="left">&lt;P1&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;Atom&gt;</td>
</tr>
<tr class="odd">
<td align="right">(33)</td>
<td align="left">&lt;P2&gt;</td>
<td align="left"><span class="math inline">→</span> <code>*</code> &lt;Atom&gt;&lt;P2&gt;</td>
</tr>
<tr class="even">
<td align="right">(34)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>/</code> &lt;Atom&gt;&lt;P2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(35)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="even">
<td align="right">(36)</td>
<td align="left">&lt;Atom&gt;</td>
<td align="left"><span class="math inline">→</span> [VarName]</td>
</tr>
<tr class="odd">
<td align="right">(37)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> [Number]</td>
</tr>
<tr class="even">
<td align="right">(38)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>-</code>&lt;Atom&gt;</td>
</tr>
<tr class="odd">
<td align="right">(39)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>(</code> &lt;ExprArith&gt; <code>)</code></td>
</tr>
<tr class="even">
<td align="right">(40)</td>
<td align="left">&lt;Cond&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;C1&gt;&lt;C2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(41)</td>
<td align="left">&lt;C1&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;BoolAnd&gt;</td>
</tr>
<tr class="even">
<td align="right">(42)</td>
<td align="left">&lt;C2&gt;</td>
<td align="left"><span class="math inline">→</span> <code>or</code> &lt;BoolAnd&gt;&lt;C2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(43)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="even">
<td align="right">(44)</td>
<td align="left">&lt;BoolAnd&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;BA1&gt;&lt;BA2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(45)</td>
<td align="left">&lt;BA1&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;BoolAtom&gt;</td>
</tr>
<tr class="even">
<td align="right">(46)</td>
<td align="left">&lt;BA2&gt;</td>
<td align="left"><span class="math inline">→</span> <code>and</code> &lt;BoolAtom&gt;&lt;BA2&gt;</td>
</tr>
<tr class="odd">
<td align="right">(47)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <span class="math inline"><em>ϵ</em></span></td>
</tr>
<tr class="even">
<td align="right">(48)</td>
<td align="left">&lt;BoolAtom&gt;</td>
<td align="left"><span class="math inline">→</span> <code>not</code> &lt;BoolAtom&gt;</td>
</tr>
<tr class="odd">
<td align="right">(49)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> &lt;ExprArith&gt; &lt;Comp&gt; &lt;ExprArith&gt;</td>
</tr>
<tr class="even">
<td align="right">(50)</td>
<td align="left">&lt;If&gt;</td>
<td align="left"><span class="math inline">→</span> <code>if</code> &lt;Cond&gt; <code>then</code> &lt;Code&gt; &lt;IfSuffix&gt;</td>
</tr>
<tr class="odd">
<td align="right">(51)</td>
<td align="left">&lt;IfSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> <code>endif</code></td>
</tr>
<tr class="even">
<td align="right">(52)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>else</code> &lt;Code&gt; <code>endif</code></td>
</tr>
<tr class="odd">
<td align="right">(53)</td>
<td align="left">&lt;Comp&gt;</td>
<td align="left"><span class="math inline">→</span> <code>=</code></td>
</tr>
<tr class="even">
<td align="right">(54)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>&gt;=</code></td>
</tr>
<tr class="odd">
<td align="right">(55)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>&gt;</code></td>
</tr>
<tr class="even">
<td align="right">(56)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>&lt;=</code></td>
</tr>
<tr class="odd">
<td align="right">(57)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>&lt;</code></td>
</tr>
<tr class="even">
<td align="right">(58)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>&lt;&gt;</code></td>
</tr>
<tr class="odd">
<td align="right">(59)</td>
<td align="left">&lt;While&gt;</td>
<td align="left"><span class="math inline">→</span> <code>while</code> &lt;Cond&gt; <code>do</code> &lt;Code&gt; <code>done</code></td>
</tr>
<tr class="even">
<td align="right">(60)</td>
<td align="left">&lt;For&gt;</td>
<td align="left"><span class="math inline">→</span> <code>for</code> [VarName] <code>from</code> &lt;ExprArith&gt; &lt;ForSuffix&gt;</td>
</tr>
<tr class="odd">
<td align="right">(61)</td>
<td align="left">&lt;ForSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> <code>by</code> &lt;ExprArith&gt; <code>to</code> &lt;ExprArith&gt; <code>do</code> &lt;Code&gt; <code>done</code></td>
</tr>
<tr class="even">
<td align="right">(62)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> <code>to</code> &lt;ExprArith&gt; <code>do</code> &lt;Code&gt; <code>done</code></td>
</tr>
<tr class="odd">
<td align="right">(63)</td>
<td align="left">&lt;Print&gt;</td>
<td align="left"><span class="math inline">→</span> <code>print(</code> &lt;PrintSuffix&gt;</td>
</tr>
<tr class="even">
<td align="right">(64)</td>
<td align="left">&lt;PrintSuffix&gt;</td>
<td align="left"><span class="math inline">→</span> &lt;ExprArith&gt;<code>)</code></td>
</tr>
<tr class="odd">
<td align="right">(65)</td>
<td align="left"></td>
<td align="left"><span class="math inline">→</span> [String]<code>)</code></td>
</tr>
<tr class="even">
<td align="right">(66)</td>
<td align="left">&lt;Read&gt;</td>
<td align="left"><span class="math inline">→</span> <code>read(</code>[VarName]<code>)</code></td>
</tr>
</tbody>
</table>