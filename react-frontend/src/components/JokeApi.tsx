export async function fetchRandomJoke(
  apiUrl: string,
  onRandomJokeFetched: (joke: any) => void
): Promise<any> {
  const url = apiUrl + "/jokes/random/";
  const response = await fetch(url);
  const joke = await response.json();
  console.log("Fetched random joke (from " + url + "):\r\n", joke);
  onRandomJokeFetched(joke);
  return joke;
}
