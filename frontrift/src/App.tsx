export default function App() {
  return (
      <div className="max-w-2x1 mx-auto p-4 flex flex-colgap-4">
      <RiftHeader></RiftHeader>
    </div>
  )
}

function RiftHeader() {
  return (
  <header className="flex items-center justify-between">
    <div className="flex flex-col gap-1">
      <h1 className="text-3xl font-bold">Riftdeck analyzer</h1>
      <span className=" text-zinc-400 text-sm"> Your decks:</span>
    </div>
    <div></div>
  </header>
  )
}
