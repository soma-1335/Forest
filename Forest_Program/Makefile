ANT	= env LC_ALL=ja_JP.UTF-8 ant

all:
	$(ANT) all

clean:
	$(ANT) clean

test:
	$(ANT) test

tree: clean
	$(ANT) tree

forest: clean
	$(ANT) forest

semilattice: clean
	$(ANT) semilattice

install:
	$(ANT) install

doc:
	$(ANT) doc

zip:
	$(ANT) zip

# 以下を追加、単体テストのために。
unittest:
	$(ANT) unittest

# 以下を追加、アサーションのために。
assert:
	$(ANT) assert
