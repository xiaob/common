package com.yuan.common.file;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FileIterator implements Iterator<File> {
    private static class State {
        final State parent;
        final File[] files;

        int index = 0;

        public State(State parent, File dir) {
            this.parent = parent;
            files = dir.listFiles();
        }
        public State(State parent, File dir, String... exts) {
        	this.parent = parent;
        	files = dir.listFiles(new DefaultFilenameFilter(exts));
        }
    }

    private File current;
    private State state;
    private String[] exts;

    public FileIterator(File file) {
        if (file.isDirectory()) {
            state = new State(null, file);
            nextInternal();
        } else {
            this.current = file;
            state = null;
        }
    }
    public FileIterator(File file, String... exts) {
    	this.exts = exts;
    	if (file.isDirectory()) {
    		state = new State(null, file, exts);
    		nextInternal();
    	} else {
    		this.current = file;
    		state = null;
    	}
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public File next() {
        File rtValue = current;

        if (rtValue == null) throw new NoSuchElementException();

        nextInternal();

        return rtValue;
    }

    private void nextInternal() {
        current = null;

        if (this.state == null) return;

        for (;;) {
            if (state.index >= state.files.length) {
                state = state.parent;
                if (state == null) return;
                state.index++;
                continue;
            }

            File file = state.files[state.index];
            
            // 可以在此处加入Filters处理代码
            
            if (file.isDirectory()) {
            	if((exts == null) || (exts.length == 0)){
            		state = new State(state, file);
            	}else{
            		state = new State(state, file, exts);
            	}
                continue;
            }

            current = file;
            state.index++;
            break;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
