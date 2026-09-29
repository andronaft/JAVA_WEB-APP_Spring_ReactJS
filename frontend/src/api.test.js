import { getJson, postForm, REQUEST_FAILED } from './api';

function mockFetch(status, body) {
  global.fetch = jest.fn().mockResolvedValue({
    ok: status >= 200 && status < 300,
    status,
    json: () => Promise.resolve(body),
  });
}

afterEach(() => {
  delete global.fetch;
});

test('postForm sends the params as a form body, not in the URL', async () => {
  mockFetch(200, ['ok']);

  const data = await postForm('/login', { login: 'a&b', password: 'p w' });

  expect(data).toEqual(['ok']);
  const [url, options] = global.fetch.mock.calls[0];
  expect(url).toBe('/login');
  expect(options.method).toBe('POST');
  expect(options.body.toString()).toBe('login=a%26b&password=p+w');
});

test('getJson uses a relative URL', async () => {
  mockFetch(200, { arrayList: [], amount: 0 });

  expect(await getJson('/getAllConference')).toEqual({ arrayList: [], amount: 0 });
  expect(global.fetch.mock.calls[0][0]).toBe('/getAllConference');
});

test('error message from the server is passed through', async () => {
  mockFetch(400, ['Please fill in all fields correctly']);

  expect(await postForm('/createconf')).toEqual(['Please fill in all fields correctly']);
});

test('unexpected errors become a generic message', async () => {
  mockFetch(500, { status: 500, error: 'Internal Server Error' });
  expect(await getJson('/getAccount')).toEqual([REQUEST_FAILED]);

  global.fetch = jest.fn().mockRejectedValue(new TypeError('Failed to fetch'));
  expect(await getJson('/getAccount')).toEqual([REQUEST_FAILED]);
});
