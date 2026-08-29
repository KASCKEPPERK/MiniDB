1
Simple Search vs HashTable lookup

Usando 10 buckets, o tempo de procura foi  ~ 0.02x menor para o hashlookup com 1M users.
Usando 100 buckets, o tempo de procura foi  ~ 0.085x menor para o hashlookup com 1M users.
Usando 1000 buckets, o tempo de procura foi  ~ 0.26x menor para o hashlookup com 1M users.

Isto demonstra a superioridade temporal de um Hash lookup, sendo o tradeoff a memoria para mais buckets.

2 
Implementei agora um metodo Resize() na HashTable  que dobra os buckets se n elementos > 3/4 nbuckets. Isto claro com o objetivo de evitar colisoes,
melhorando assim a performance do lookup na HashTable.

Começei com 10 buckets e os resultados finais foram como esperado:
O tempo de procura foi 10x menor para o hashlookup com 1M users e o número de buckets finais foram 2621440.
As colisoes estao a ser bem evitadas.

Vamos agora iniciar já com esse número de buckets sem resize para ver o tradeoff memoria x tempo (ganhamos a memoria de irmos ajustando o len dos buckets aos poucos, vamos ver se compensa o tempo)
Usando 2621440 buckets, o tempo de procura foi  ~ 11.73x menor para o hashlookup com 1M users.
Valeu sim! a diferença de tempo não é grande tendo em conta a memoria que salvamos!

3
Implementei metodos para guardar a database num ficheiro (ou seja a tabela). Ler o ficherio uma vez o ficheiro no incio para reconstruir o hashmap que agora guarda o id e o offset(posiçao da row no ficheiro).

4
Criei um metodo a lembrar um where com between do SQL só que bastante rudimentar, jã que um HashMap não preserva ordem.
O metodo consiste em ir lendo no ficheiro e ir verificando o idx.

Inseri  1000000 rows na DB WHERE id BETWEEN 500000 AND 500010 e demorou ~17.3 s. (11 resultados como esperado).

5
Criei uma classe B+Tree para conseguir melhorar o meotodo 'Where Between' agora o objetivo é procurar em o(logn) ao em vez de o(n)
1M users, comparei o meotodo anterior com este nvoo metodo, e deu uma diferença brutal.
Scan Normal 19.44 sec
Scan Btree+ 0.0002 sec





