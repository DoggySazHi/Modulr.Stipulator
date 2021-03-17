# Modulr.Stipulator
The new core for the server-based Java stipulator, written in ~~C#~~ Java.

Because why not.

## What is this?
An attempt to replace JUnit as a test runner, Modulr.Stipulator is an attempt to reduce external code use.

Plus, I just wanted to control the way it prints output.
## Features
- Less than JUnit, that's for sure.
  - A drop-in replacement for JUnit 5 suites, so there should be minimal rewriting.
- Fuzzing, based on existing code samples (e.g. teacher code vs student code)
- Live testing progress (with Modulr's websocket capability)
## Contribution
Just like the core Modulr project, for all problems, please place them in issues. PRs are welcome, though I'm not sure how to handle them.

## License
Honestly, I don't care whoever uses this, since it's just a personal project. However, if you really care, this is under the [MIT](https://choosealicense.com/licenses/mit/) license.

```
MIT License

Copyright (c) 2021 William Le

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
