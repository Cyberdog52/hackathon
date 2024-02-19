import Axios from "axios";

export async function fetchNames(
  baseUrl: string,
  onNamesFetched: (names: string[]) => void
): Promise<string[]> {
  const { data } = await Axios.get(baseUrl + "/persons/names/");
  const names = data;
  console.log("Fetched names:", names);
  onNamesFetched(names);
  return names;
}

export async function fetchDetails(
  baseUrl: string,
  name: string,
  onDetailsFetched: (details: any) => void
): Promise<any> {
  const { data } = await Axios.get(baseUrl + "/persons/" + name + "/details/");
  const details = data;
  console.log("Fetched details: ", details);
  onDetailsFetched(details);
  return details;
}
