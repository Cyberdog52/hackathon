export async function fetchDiagramNames(
  apiUrl: string,
  onDiagramNamesFetched: (names: string[]) => void
): Promise<string[]> {
  const url = apiUrl + "/diagrams/names/";
  const response = await fetch(url);
  const names = await response.json();
  console.log("Fetched diagram names (from " + url + "):\r\n", names);
  onDiagramNamesFetched(names);
  return names;
}

export async function fetchDiagram(
  apiUrl: string,
  name: string,
  onDiagramDataFetched: (details: any) => void
): Promise<any> {
  if (name.length === 0) {
    return undefined;
  }

  // DON'T DO THAT (concatenating strings coming from the UI; I have to fix this :-| )
  const url = apiUrl + "/diagrams/" + name + "/data/";
  const response = await fetch(url);
  const details = await response.json();
  console.log("Fetched diagram (from "+url+"):\r\n", details);
  onDiagramDataFetched(details);
  return details;
}
