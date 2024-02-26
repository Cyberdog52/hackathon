import Axios from "axios";

export async function fetchDiagramNames(
  apiUrl: string,
  onNamesFetched: (names: string[]) => void
): Promise<string[]> {
  const { data } = await Axios.get(apiUrl + "/diagrams/names/");
  const names = data;
  console.log("Fetched diagram names:", names);
  onNamesFetched(names);
  return names;
}

export async function fetchDiagram(
  apiUrl: string,
  name: string,
  onDetailsFetched: (details: any) => void
): Promise<any> {
  const { data } = await Axios.get(apiUrl + "/diagrams/" + name + "/details/");
  let details = data;
  details.imageUrl = apiUrl + details.imageUrl;
  console.log("Fetched diagram: ", details);
  onDetailsFetched(details);
  return details;
}
