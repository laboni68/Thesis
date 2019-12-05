\REQUIRE{$C$ = {set of unused cameras}; $T$ = {set of uncovered targets}; $P$ = {set of discrete pans}}
\ENSURE{$Z$ = {set of sensor-orientation pairs given by TMnCH}}
\STATE model the conflict graph, $G = (V,E)$ where $V$ = \{set of nodes (sensors)\}; $E$ = \{set of weighted edges\}
\WHILE{$C \neq \phi ~\OR~ T \neq \phi$}
  	\FOR{each $c_i \in C$}
		{\STATE $sum_i$ = sum of conflicts with all $p_k \in P$ of $c_j \in C$ for $\forall j \neq i$}
    \ENDFOR
    \STATE $c_i = min(sum_i ~\forall~ c_i \in C)$ \COMMENT{select the sensor with minimum conflicts}
    \FOR{each $p_k \in P ~of~ c$}
		{\STATE $p_k = max(|t_k|)$ \COMMENT{find the orientation covering maximum targets}}
    \ENDFOR
    \STATE $\tau$ = set of targets covered by $\langle c_i,p_k \rangle$ sensor-orientation pair
    \STATE $C = C\backslash\{c_i\};~T = T\backslash\{\tau\}$
    \STATE $Z = Z~\cup~\{\langle c_i,p_k \rangle\}$
    \STATE $V^{'} = V\backslash\{c_i\}$
    \STATE $E^{'} = E\backslash\{$set of edges connected to $c_i\}$
    \STATE update weight of the edges of the neighboring nodes of $c_i$
    \STATE $G = (V^{'},E^{'})$
    \IF{$t_{ik} = \phi ~\forall~ p_k \in P ~of~ c_i \in C$}
		\STATE $break$
    \ENDIF
\ENDWHILE


\endinput


