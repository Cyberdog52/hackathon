import Axios from "axios";

export async function fetchPersonNames(
  apiUrl: string,
  onNamesFetched: (names: string[]) => void
): Promise<string[]> {
  const { data } = await Axios.get(apiUrl + "/persons/names/");
  const names = data;
  console.log("Fetched names:", names);
  onNamesFetched(names);
  return names;
}

export async function fetchPersonDetails(
  apiUrl: string,
  name: string,
  onDetailsFetched: (details: any) => void
): Promise<any> {
  const { data } = await Axios.get(apiUrl + "/persons/" + name + "/details/");
  let details = data;
  details.imageUrl = apiUrl + details.imageUrl;
  console.log("Fetched details: ", details);
  onDetailsFetched(details);
  return details;
}
