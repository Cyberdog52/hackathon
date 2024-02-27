import Axios from "axios";

export async function fetchDiagramNames(
  apiUrl: string,
  onDiagramNamesFetched: (names: string[]) => void
): Promise<string[]> {
  const { data } = await Axios.get(apiUrl + "/diagrams/names/");
  const names = data;
  console.log("Fetched diagram names:", names);
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

  const { data } = await Axios.get(apiUrl + "/diagrams/" + name + "/data/");
  const details = data;
  console.log("Fetched diagram: ", details);
  onDiagramDataFetched(details);
  return details;
}
