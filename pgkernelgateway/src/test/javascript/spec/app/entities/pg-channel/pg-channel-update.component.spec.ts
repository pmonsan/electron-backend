/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelUpdateComponent } from 'app/entities/pg-channel/pg-channel-update.component';
import { PgChannelService } from 'app/entities/pg-channel/pg-channel.service';
import { PgChannel } from 'app/shared/model/pg-channel.model';

describe('Component Tests', () => {
  describe('PgChannel Management Update Component', () => {
    let comp: PgChannelUpdateComponent;
    let fixture: ComponentFixture<PgChannelUpdateComponent>;
    let service: PgChannelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgChannelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgChannelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgChannel(123);
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
        const entity = new PgChannel();
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
