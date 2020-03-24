Lab. 5:
- Am terminat partea optionala, e functionala, suporta comenzile:
	-load <catalogPath>
	-list <catalogName>
	-save <catalogName>
	-view <catalogName>
	-reportHTML <catalogName>
- Pentru a tine evidenta cataloagelor incarcate, am folosit o clasa CatalogManager; fiecare comanda are acces la
o instanta a CatalogManager pentru a identifica catalogul al carui nume este dat ca argument sau, in cazul
functiei load, pentru adaugarea catalogului din catalogPath la lista de cataloage.