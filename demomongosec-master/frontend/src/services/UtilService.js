class Util {
 clonaProxy(p) {
  return JSON.parse(JSON.stringify(p));
 }
}

export default new Util();