/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ServiceChannelUpdateComponent } from 'app/entities/service-channel/service-channel-update.component';
import { ServiceChannelService } from 'app/entities/service-channel/service-channel.service';
import { ServiceChannel } from 'app/shared/model/service-channel.model';

describe('Component Tests', () => {
  describe('ServiceChannel Management Update Component', () => {
    let comp: ServiceChannelUpdateComponent;
    let fixture: ComponentFixture<ServiceChannelUpdateComponent>;
    let service: ServiceChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ServiceChannelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceChannelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceChannelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceChannelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceChannel(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceChannel();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
