function showhide(layer_ref)
{

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "state = document.all." + layer_ref + ".style.display");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		state = document.layers[layer_ref].display;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		state = hza.style.display;
	}

	if (state == 'block' || state == 'inline')
	{
		state = 'none';
	}
	else
	{
		state = 'block';
	}

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "document.all." + layer_ref + ".style.display = state");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		document.layers[layer_ref].display = state;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		hza.style.display = state;
	}
}


function show(layer_ref)
{

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "state = document.all." + layer_ref + ".style.display");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		state = document.layers[layer_ref].display;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		state = hza.style.display;
	}

	if (state == 'none')
	{
		state = 'block';
	}

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "document.all." + layer_ref + ".style.display = state");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		document.layers[layer_ref].display = state;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		hza.style.display = state;
	}
}

function hide(layer_ref)
{

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "state = document.all." + layer_ref + ".style.display");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		state = document.layers[layer_ref].display;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		state = hza.style.display;
	}

	if (state == 'block')
	{
		state = 'none';
	}

	if (document.all) //IS IE 4 or 5 (or 6 beta)
	{
		eval( "document.all." + layer_ref + ".style.display = state");
	}
	if (document.layers) //IS NETSCAPE 4 or below
	{
		document.layers[layer_ref].display = state;
	}
	if (document.getElementById &&!document.all)
	{
		hza = document.getElementById(layer_ref);
		hza.style.display = state;
	}
}