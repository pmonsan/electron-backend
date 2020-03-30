/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { Pg8583RequestUpdateComponent } from 'app/entities/pg-8583-request/pg-8583-request-update.component';
import { Pg8583RequestService } from 'app/entities/pg-8583-request/pg-8583-request.service';
import { Pg8583Request } from 'app/shared/model/pg-8583-request.model';

describe('Component Tests', () => {
  describe('Pg8583Request Management Update Component', () => {
    let comp: Pg8583RequestUpdateComponent;
    let fixture: ComponentFixture<Pg8583RequestUpdateComponent>;
    let service: Pg8583RequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [Pg8583RequestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Pg8583RequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Pg8583RequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Pg8583RequestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pg8583Request(123);
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
        const entity = new Pg8583Request();
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
