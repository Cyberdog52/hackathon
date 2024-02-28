import Axios from "axios";

export async function fetchRandomJoke(
  apiUrl: string,
  onRandomJokeFetched: (joke: any) => void
): Promise<any> {
  const url = apiUrl + "/jokes/random/";
  const { data } = await Axios.get(url);
  const joke = data;
  console.log("Fetched random joke (from " + url + "):\r\n", joke);
  onRandomJokeFetched(joke);
  return joke;
}
