/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgChannelAuthorizedUpdateComponent } from 'app/entities/pg-channel-authorized/pg-channel-authorized-update.component';
import { PgChannelAuthorizedService } from 'app/entities/pg-channel-authorized/pg-channel-authorized.service';
import { PgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

describe('Component Tests', () => {
  describe('PgChannelAuthorized Management Update Component', () => {
    let comp: PgChannelAuthorizedUpdateComponent;
    let fixture: ComponentFixture<PgChannelAuthorizedUpdateComponent>;
    let service: PgChannelAuthorizedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgChannelAuthorizedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PgChannelAuthorizedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgChannelAuthorizedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgChannelAuthorizedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PgChannelAuthorized(123);
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
        const entity = new PgChannelAuthorized();
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
