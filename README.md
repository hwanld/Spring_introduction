# Spring_introduction
<h2>Spring을 공부 기록용 만든 repository</h2>

2021.09.14) 회원의 id와 name을 저장하고 반환하는 등의 간단한 서비스를 제작하기 시작함. DB가 아직 구현되어 있지 않고, 어떤 DB를 쓸지 모른다고 가정이 되어 있기 때문에 우선적으로 repository를 만들고 interface를 사용해서 구현한다. 나중에 DB가 선택이 되면 그때 interface만 수정하면 되기 때문에 interface를 우선 만든다. 이와 같이 설계한다면 나중에 interface를 변경함으로써 구현 클래스를 변경할 수 있다. 이후 그 interface를 implement하는 MemoryMemberRepository라는 class를 만들어서 구현한다. 또한 개발을 진행하기 위해서 우선 초기 개발 단계에서는 구현체를 이용해서 가벼운 메모리 기반의 저장소를 사용한다. (실무에서는 list가 사용하기 편하기 때문에 list를 많이 사용한다)
