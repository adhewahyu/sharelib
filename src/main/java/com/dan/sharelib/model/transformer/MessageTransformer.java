package com.dan.sharelib.model.transformer;


public interface MessageTransformer <I , O>{

    O transform(I input);

}
